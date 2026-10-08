const BASE_URL = "https://vickbooks-api.onrender.com";
const API_URL = `${BASE_URL}/api/v1/livros`;
const IMAGE_URL = `${BASE_URL}/capas`;

let books = [];
let activeCategory = "Todos";
let favorites = JSON.parse(localStorage.getItem("vickbooks-favorites") || "[]");

const bookGrid = document.getElementById("bookGrid");
const favoriteGrid = document.getElementById("favoriteGrid");
const favoriteMessage = document.getElementById("favoriteMessage");
const emptyState = document.getElementById("emptyState");
const searchInput = document.getElementById("searchInput");
const categories = document.querySelectorAll(".category");
const menuBtn = document.getElementById("menuBtn");
const nav = document.querySelector(".nav");

function getToken() {
  return localStorage.getItem("vickbooks-token");
}

function authHeaders() {
  const token = getToken();
  return token ? { Authorization: `Bearer ${token}` } : {};
}

async function loadBooks() {
  try {
    const response = await fetch(API_URL, {
      headers: authHeaders()
    });

    if (response.status === 401 || response.status === 403) {
      localStorage.removeItem("vickbooks-token");
      localStorage.removeItem("vickbooks-user");
      throw new Error("Sua sessao expirou. Faca login novamente.");
    }

    if (!response.ok) {
      throw new Error(`Erro na requisicao: ${response.status}`);
    }

    books = await response.json();
    console.log("Livros carregados com sucesso:", books);

    renderBooks();
    renderFavorites();
  } catch (error) {
    console.error("Erro ao carregar livros:", error);
    if (emptyState) {
      emptyState.style.display = "block";
      emptyState.querySelector("h3").textContent = "Nao foi possivel carregar os livros";
      emptyState.querySelector("p").textContent = error.message;
    }
  }
}

function saveFavorites() {
  localStorage.setItem("vickbooks-favorites", JSON.stringify(favorites));
}

function createBookCard(book) {
  const isFavorite = favorites.includes(book.id);

  let imagemUrl = null;

  if (book.capa) {
    const nomeArquivo = book.capa.split(/[\\\/]/).pop().trim();

    if (nomeArquivo) {
      imagemUrl = `${IMAGE_URL}/${encodeURIComponent(nomeArquivo)}`;
    }
  }


  return `
    <article class="book-card">
      <div class="cover" style="width: 100%; height: 260px; overflow: hidden; background-color: #eee; position: relative;">
        ${
          imagemUrl
            ? `<img src="${imagemUrl}" alt="Capa de ${escapeHtml(book.titulo)}" style="width: 100%; height: 100%; object-fit: cover; display: block;" />`
            : `<div class="cover-inner" style="display: flex; flex-direction: column; align-items: center; justify-content: center; height: 100%; text-align: center; padding: 12px;">
                 <small>VICKBOOKS</small>
                 <strong>${escapeHtml(book.titulo)}</strong>
                 <small>${escapeHtml(book.categoria || "")}</small>
               </div>`
        }
      </div>

      <div class="card-body">
        <div class="card-category">${escapeHtml(book.categoria || "Livro")}</div>
        <h3 class="card-title">${escapeHtml(book.titulo || "Sem titulo")}</h3>
        <p class="card-author">${escapeHtml(book.autor || "Autor desconhecido")}</p>

        <div class="card-footer">
          <button class="read-btn" type="button" onclick="downloadBook(${book.id})">
            Baixar livro →
          </button>

          <button class="favorite-btn ${isFavorite ? "active" : ""}" type="button" aria-label="Favoritar ${escapeHtml(book.titulo)}" onclick="toggleFavorite(${book.id})">
            ${isFavorite ? "♥" : "♡"}
          </button>
        </div>
      </div>
    </article>
  `;
}

function escapeHtml(value) {
  return String(value ?? "")
    .replace(/&/g, "&amp;")
    .replace(/</g, "&lt;")
    .replace(/>/g, "&gt;")
    .replace(/"/g, "&quot;")
    .replace(/'/g, "&#039;");
}

function getFilteredBooks() {
  const query = searchInput ? searchInput.value.trim().toLowerCase() : "";

  return books.filter(book => {
    const matchesCategory = activeCategory === "Todos" || book.categoria === activeCategory;
    const titulo = String(book.titulo || "").toLowerCase();
    const autor = String(book.autor || "").toLowerCase();
    const matchesSearch = !query || titulo.includes(query) || autor.includes(query);

    return matchesCategory && matchesSearch;
  });
}

function renderBooks() {
  if (!bookGrid) return;
  const filtered = getFilteredBooks();

  bookGrid.innerHTML = filtered.map(createBookCard).join("");

  if (emptyState) {
    emptyState.style.display = filtered.length ? "none" : "block";
  }
  
}

function renderFavorites() {
  if (!favoriteGrid) return;
  const favoriteBooks = books.filter(book => favorites.includes(book.id));

  favoriteGrid.innerHTML = favoriteBooks.map(createBookCard).join("");

  if (favoriteMessage) {
    favoriteMessage.style.display = favoriteBooks.length ? "none" : "flex";
  }
}

function toggleFavorite(id) {
  if (favorites.includes(id)) {
    favorites = favorites.filter(bookId => bookId !== id);
  } else {
    favorites.push(id);
  }

  saveFavorites();
  renderBooks();
  renderFavorites();
}

async function downloadBook(id) {
  try {
    const response = await fetch(`${API_URL}/${id}/download`, {
      headers: authHeaders()
    });

    if (response.status === 401 || response.status === 403) {
      alert("Faca login para baixar o livro.");
      window.location.href = "login.html";
      return;
    }

    if (!response.ok) {
      throw new Error(`Erro ao baixar: ${response.status}`);
    }

    const blob = await response.blob();
    const url = URL.createObjectURL(blob);
    const link = document.createElement("a");
    link.href = url;
    link.download = "livro.epub";
    document.body.appendChild(link);
    link.click();
    link.remove();
    URL.revokeObjectURL(url);
  } catch (error) {
    console.error(error);
    alert("Nao foi possivel baixar o livro.");
  }
}

if (searchInput) {
  searchInput.addEventListener("input", renderBooks);
}

categories.forEach(category => {
  category.addEventListener("click", () => {
    categories.forEach(item => item.classList.remove("active"));
    category.classList.add("active");
    activeCategory = category.dataset.category;
    renderBooks();
  });
});

if (menuBtn && nav) {
  menuBtn.addEventListener("click", () => {
    nav.classList.toggle("open");
  });
}

document.querySelectorAll(".nav a").forEach(link => {
  link.addEventListener("click", () => {
    if (nav) nav.classList.remove("open");
  });
});

loadBooks();
