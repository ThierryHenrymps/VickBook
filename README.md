# VickBook

O **VickBook** e uma plataforma para organizacao e disponibilizacao de livros digitais.

O projeto foi desenvolvido com frontend, backend e banco de dados, permitindo gerenciar os livros e suas informacoes.

## Funcionalidades

- Listagem de livros
- Busca por titulo
- Busca por autor
- Consulta de livros
- Cadastro de livros
- Atualizacao de livros
- Exclusao de livros
- Organizacao por categorias
- Exibicao das capas
- Disponibilizacao dos arquivos digitais

## Tecnologias utilizadas

### Frontend

- HTML
- CSS
- JavaScript

### Backend

- Java
- Spring Boot
- Spring Data JPA
- Spring Web
- Maven

### Banco de dados

- MySQL

## API

A aplicacao possui uma API REST responsavel pelo gerenciamento dos livros.

Principais operacoes:

- GET - consultar livros
- POST - cadastrar livros
- PUT - atualizar livros
- DELETE - excluir livros

## Estrutura do projeto

```text
VickBook/
├── back/
│   └── book/
│
├── front/
│
├── .gitignore
└── README.md
```

## Funcionamento

O usuario acessa o frontend para visualizar e pesquisar os livros.

O frontend se comunica com o backend por meio de uma API REST. O backend realiza o gerenciamento das informacoes e a comunicacao com o banco de dados.

```text
Usuario
   |
   v
Frontend
   |
   v
Backend
   |
   v
Banco de dados
```

## Objetivo

O VickBook foi desenvolvido como um projeto pratico para aplicar conhecimentos de desenvolvimento web, Java, Spring Boot, APIs REST, banco de dados e integracao entre frontend e backend.

## Autor
**UFCI-MG**
**Thierry Henry Moreira Pimenta Silva**
