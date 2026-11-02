# SoulHunter — API Restful (Sprint 4)

**FIAP · Domain Driven Design Using Java · Entrega final do Challenge (Sprint 4)**

O SoulHunter é um sistema de gamificação de ações sustentáveis. O usuário registra reciclagem e uso de transporte sustentável, ganha **Essência** e sobe de **Título de Caçador**. Ele também pode capturar **Fantasmas**, que ficam ligados ao seu cadastro.

Nesta Sprint, a aplicação virou uma **API Restful em Spring Boot**. Ela usa banco **Oracle**, está integrada a um **front-end em React** e foi publicada na nuvem pelo **Render**.

---

## 🔗 Links da entrega

| Item | Link |
|---|---|
| **Deploy da API (Render)** | https://soulhunter-rm571411.onrender.com |
| Exemplo de consulta | https://soulhunter-rm571411.onrender.com/soulhunter/usuarios |
| **Front-end publicado (Vercel)** | https://soulhunter-front.vercel.app |
| **Documentação final (PDF)** | https://github.com/cearaa/Sprint4JavaBDFront/blob/main/documentacao_soulhunter_sprint4.pdf |
| **Código-fonte Java (.zip)** | [Sprint4JavaBDFront.zip](Sprint4JavaBDFront.zip) |
| Código-fonte Java (pastas) | [src/main/java/br/com/fiap](src/main/java/br/com/fiap) |


### 🌐 Por que o front-end também está hospedado

O enunciado pede o deploy da API. Mesmo assim, o grupo também publicou o **front-end na Vercel** para **facilitar a correção**:

- **Basta abrir o link.** Não é preciso instalar o Node.js nem rodar `npm install` e `npm start`.
- **Mostra a integração funcionando.** O front publicado já consome a API do Render, então dá para ver o sistema completo (cadastro, reciclagem, transporte e fantasmas) direto no navegador.
- **O código continua no repositório.** A pasta [`front-end`](front-end) tem o projeto React, para quem quiser rodar no computador (veja a seção "Como rodar o front-end").

> ⏳ **Atenção:** no plano gratuito do Render, a API "dorme" depois de um tempo sem uso. A **primeira requisição pode levar cerca de 1 minuto**. Depois disso, as respostas são rápidas.

---

## 👥 Integrantes

| Nome | RM |
|---|---|
| Italo Neto | 572912 |
| Giovanni Azevedo | 572894 |
| Tárik Moussa Alma | 571411 |
| Fabricio Aquiles Sales da Silva | 570985 |
| Carlos Eduardo Tsucamoto Chiarelli | 569574 |

---

## 🛠️ Tecnologias

- **Java 21+** e **Spring Boot 3.5.7** (Spring Web e Bean Validation)
- **Maven** (com Maven Wrapper `mvnw`)
- **Oracle Database**, com driver **ojdbc11**
- **React** (Create React App) no front-end
- **Docker** e **Render** para o deploy

---

## 📁 Estrutura do projeto

```
Sprint4JavaBDFront/
├── src/main/java/br/com/fiap/
│   ├── to/          → modelos (UsuarioTO, FantasmaTO, ReciclagemTO, TransporteTO)
│   ├── dao/         → acesso ao banco Oracle (UsuarioDAO, FantasmaDAO, ConnectionFactory)
│   ├── bo/          → regras de negócio (UsuarioBO, FantasmaBO)
│   ├── resource/    → endpoints REST (UsuarioResource, FantasmaResource, CorsFilter)
│   └── SoulHunterApplication.java
├── front-end/       → aplicação React que consome a API
├── documentacao/    → documentação final em PDF
├── Sprint4JavaBDFront.zip → pasta do projeto Java compactada
├── Dockerfile       → usado no deploy do Render
└── pom.xml
```

---

## ✅ Funcionalidades

- **Usuários:** cadastrar, alterar, excluir, listar e consultar. O e-mail não pode se repetir, e não é possível excluir um usuário que ainda tem fantasmas.
- **Reciclagem:** soma **5 de Essência por kg** reciclado.
- **Transporte:** soma **3 de Essência por km de bicicleta** e **2 por km nos outros meios**.
- **Fantasmas:** capturar, alterar, excluir, listar e consultar. Todo fantasma fica ligado a um usuário.
- **Título de Caçador:** é recalculado a cada ação.

| Essência acumulada | Título |
|---|---|
| menos de 30 | Aprendiz de Caçador |
| de 30 até menos de 60 | Caçador Iniciante |
| de 60 até menos de 100 | Caçador Raro |
| 100 ou mais | Caçador Lendário |

---

## 📡 Endpoints

URL base: `https://soulhunter-rm571411.onrender.com` (no computador: `http://localhost:8080`)

### Usuários

| Verbo | URI | Descrição |
|---|---|---|
| GET | `/soulhunter/usuarios` | Lista todos os usuários |
| GET | `/soulhunter/usuarios/{codigo}` | Busca um usuário pelo código |
| GET | `/soulhunter/usuarios/{codigo}/fantasmas` | Lista os fantasmas de um usuário |
| POST | `/soulhunter/usuarios` | Cadastra um usuário |
| PUT | `/soulhunter/usuarios/{codigo}` | Altera nome e e-mail |
| DELETE | `/soulhunter/usuarios/{codigo}` | Exclui o usuário (bloqueado se tiver fantasmas) |
| POST | `/soulhunter/usuarios/{codigo}/reciclagem` | Registra uma reciclagem |
| POST | `/soulhunter/usuarios/{codigo}/transporte` | Registra um transporte sustentável |

### Fantasmas

| Verbo | URI | Descrição |
|---|---|---|
| GET | `/soulhunter/fantasmas` | Lista todos os fantasmas |
| GET | `/soulhunter/fantasmas/{codigo}` | Busca um fantasma pelo código |
| POST | `/soulhunter/fantasmas` | Captura (cadastra) um fantasma |
| PUT | `/soulhunter/fantasmas/{codigo}` | Altera um fantasma |
| DELETE | `/soulhunter/fantasmas/{codigo}` | Exclui um fantasma |

### Exemplos de JSON

```json
POST /soulhunter/usuarios
{ "nome": "Maria Silva", "email": "maria@email.com" }

POST /soulhunter/usuarios/1/reciclagem
{ "tipoMaterial": "Plástico", "quilos": 2 }

POST /soulhunter/usuarios/1/transporte
{ "tipoTransporte": "Bicicleta", "km": 10 }

POST /soulhunter/fantasmas
{ "nome": "Espectro Verde", "tipo": "Natureza", "raridade": "Raro", "valorEssencia": 15, "codUsuario": 1 }
```

### Códigos de status

| Código | Quando ocorre |
|---|---|
| 200 OK | Consulta ou ação registrada com sucesso |
| 201 Created | Registro criado (POST) ou alterado (PUT) |
| 204 No Content | Registro excluído |
| 400 Bad Request | Dados inválidos ou regra de negócio violada |
| 404 Not Found | Usuário ou fantasma não encontrado |
| 500 Internal Server Error | Falha interna (por exemplo, erro no banco) |

---

## ▶️ Como rodar a API no computador

1. **Crie as tabelas** no Oracle SQL Developer:

```sql
CREATE TABLE DDD_USUARIOS (
    CODIGO   NUMBER GENERATED ALWAYS AS IDENTITY,
    NOME     VARCHAR2(100) NOT NULL,
    EMAIL    VARCHAR2(100) NOT NULL,
    ESSENCIA NUMBER(10,2) DEFAULT 0 NOT NULL,
    TITULO   VARCHAR2(30),
    PRIMARY KEY (CODIGO),
    UNIQUE (EMAIL)
);

CREATE TABLE DDD_FANTASMAS (
    CODIGO         NUMBER GENERATED ALWAYS AS IDENTITY,
    NOME           VARCHAR2(50) NOT NULL,
    TIPO           VARCHAR2(30) NOT NULL,
    RARIDADE       VARCHAR2(30) NOT NULL,
    VALOR_ESSENCIA NUMBER(10,2) NOT NULL,
    COD_USUARIO    NUMBER NOT NULL,
    PRIMARY KEY (CODIGO),
    FOREIGN KEY (COD_USUARIO) REFERENCES DDD_USUARIOS (CODIGO)
);
```

2. **Abra o projeto** no IntelliJ IDEA (`File > Open`, na pasta que tem o `pom.xml`) e espere o Maven baixar as dependências.

3. **Configure as variáveis de ambiente** em `Run > Edit Configurations > SoulHunterApplication > Environment variables`:

```
DB_URL=jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL;DB_USER=seu_usuario;DB_PASSWORD=sua_senha
```

> 🔒 O usuário e a senha do banco **não ficam no código**. A classe `ConnectionFactory` lê esses dados das variáveis de ambiente.

4. **Rode** a classe `br.com.fiap.SoulHunterApplication`. Quando aparecer `Started SoulHunterApplication`, acesse http://localhost:8080/soulhunter/usuarios. Com o banco vazio, a resposta é `[]`.

---

## 💻 Como rodar o front-end

1. Instale o **Node.js 18 ou superior**.
2. Abra a pasta `front-end` no terminal e rode:

```bash
npm install
npm start
```

3. Abra http://localhost:3000 no navegador.

O front já está apontando para a API do Render. Para usar a API local, troque a constante `API_URL` em `front-end/src/api.js` por `http://localhost:8080/soulhunter`.

---

## ☁️ Deploy

A API é publicada no **Render** como Web Service, usando o `Dockerfile` do projeto. As variáveis `DB_URL`, `DB_USER` e `DB_PASSWORD` ficam cadastradas no próprio Render, em **Environment Variables**.
