const BASE_URL = "http://localhost:8080";
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

async function loadBooks() {
  try {
    const response = await fetch(API_URL);

    if (!response.ok) {
      throw new Error(`Erro na requisição: ${response.status}`);
    }

    books = await response.json();
    console.log("Livros carregados com sucesso:", books);

    renderBooks();
    renderFavorites();
  } catch (error) {
    console.error("Erro ao carregar livros:", error);
  }
}

function saveFavorites() {
  localStorage.setItem("vickbooks-favorites", JSON.stringify(favorites));
}

function createBookCard(book) {
  const isFavorite = favorites.includes(book.id);

  let imagemUrl = null;
  if (book.capa) {
    const nomeArquivo = book.capa.replace(/^.*[\\\/]/, "").trim();
    if (nomeArquivo) {
      imagemUrl = `${IMAGE_URL}/${nomeArquivo}`;
    }
  }

  return `
    <article class="book-card">
      <div class="cover" style="width: 100%; height: 260px; overflow: hidden; background-color: #eee; position: relative;">
        ${
          imagemUrl
            ? `<img 
                 src="${imagemUrl}" 
                 alt="Capa de ${book.titulo}" 
                 style="width: 100%; height: 100%; object-fit: cover; display: block;"
               />`
            : `<div class="cover-inner" style="display: flex; flex-direction: column; align-items: center; justify-content: center; height: 100%; text-align: center; padding: 12px;">
                 <small>VICKBOOKS</small>
                 <strong>${book.titulo}</strong>
                 <small>${book.categoria}</small>
               </div>`
        }
      </div>

      <div class="card-body">
        <div class="card-category">${book.categoria}</div>
        <h3 class="card-title">${book.titulo}</h3>
        <p class="card-author">${book.autor}</p>

        <div class="card-footer">
          <button
            class="read-btn"
            type="button"
            onclick="downloadBook(${book.id})"
          >
            Baixar livro →
          </button>

          <button
            class="favorite-btn ${isFavorite ? "active" : ""}"
            type="button"
            aria-label="Favoritar ${book.titulo}"
            onclick="toggleFavorite(${book.id})"
          >
            ${isFavorite ? "♥" : "♡"}
          </button>
        </div>
      </div>
    </article>
  `;
}

function getFilteredBooks() {
  const query = searchInput ? searchInput.value.trim().toLowerCase() : "";

  return books.filter(book => {
    const matchesCategory =
      activeCategory === "Todos" || book.categoria === activeCategory;

    const matchesSearch =
      !query ||
      book.titulo.toLowerCase().includes(query) ||
      book.autor.toLowerCase().includes(query);

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

function downloadBook(id) {
  window.location.href = `${API_URL}/${id}/download`;
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