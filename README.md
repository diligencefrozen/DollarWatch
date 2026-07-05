# DollarWatch

DollarWatch is a Spring MVC portfolio project for tracking exchange rates, saving favorite currency pairs, reviewing recent searches, and discussing FX moves through a simple community board.

## Repository description

> A Spring MVC web app for tracking exchange rates, saving favorite currency pairs, and discussing FX topics.

## What it does

- Looks up current exchange rates with AJAX.
- Shows a recent 7-day currency trend.
- Saves recent search logs by member or guest session state.
- Supports member registration, login, and logout.
- Lets logged-in users save and delete favorite currency pairs.
- Shows refreshed rates inside the favorites page.
- Supports community posts with optional image uploads.
- Supports comments with owner-only deletion.
- Stores members, search logs, favorites, board posts, image names, and comments in Oracle DB.

## Tech stack

| Layer | Tech |
| --- | --- |
| Backend | Java, Spring MVC 3.1.1 |
| Persistence | MyBatis, Oracle XE |
| Frontend | JSP, JSTL, jQuery, AJAX |
| Build | Maven WAR |
| Server target | Apache Tomcat |
| External APIs | ExchangeRate-API Open Access, Frankfurter v2 |

## Project structure

```text
src/main/java/com/jeesung/dollarwatch
├── main
├── member
├── exchange
├── favorite
└── board

src/main/resources
├── memberMapper.xml
├── exchangeMapper.xml
├── favoriteMapper.xml
├── boardMapper.xml
├── commentMapper.xml
└── database.properties.example

src/main/webapp
├── resources
│   ├── css
│   ├── js
│   └── upload/board
└── WEB-INF
    ├── spring
    └── views
```

## Feature map

| Module | Main responsibility |
| --- | --- |
| `main` | Home route and recent search log preparation |
| `member` | Join, login, logout, session state |
| `exchange` | Current rate lookup, 7-day trend lookup, search logging |
| `favorite` | Member-specific saved currency pairs and current-rate refresh |
| `board` | Community posts, image upload, comments, owner-only deletion |

## Setup

### 1. Configure Oracle

Create an Oracle user for the app, then run:

```sql
@database/schema.sql
```

### 2. Configure database credentials

Copy the example file:

```bash
cp src/main/resources/database.properties.example src/main/resources/database.properties
```

Edit `database.properties`:

```properties
db.url=jdbc:oracle:thin:@localhost:1521:xe
db.username=dollarwatch
db.password=your_password
```

### 3. Build

```bash
mvn clean package
```

### 4. Deploy

Deploy `target/DollarWatch.war` to Tomcat.

## Demo account

```text
ID: demo
PW: 1234
```

The demo account is created by `database/schema.sql`.

## Portfolio notes

This repo was reconstructed from Notion development notes. The cleaned final version keeps the portfolio-ready feature set and removes mixed-in lecture examples, old drafts, duplicated mappings, local credentials, and scratch code.

Key finalization decisions:

- Normalized the package namespace to `com.jeesung.dollarwatch`.
- Completed the final modules: member, exchange, favorite, board, and comments.
- Added recent search-log retrieval to make the existing `dollar_search_log` table visible in the UI.
- Added duplicate prevention for favorite currency pairs.
- Changed destructive actions such as favorite delete and board delete to POST forms.
- Kept image upload support for board posts and cleans up uploaded files when a post is deleted.
- Moved DB credentials to `database.properties` and ignored it from Git.
- Added a clean Oracle schema file under `database/schema.sql`.

## Main points to highlight in a portfolio

1. **Practical MVC structure**: controller, DAO/service, mapper, and JSP view separation.
2. **External API integration**: current exchange lookup plus time-series trend fetch.
3. **AJAX interaction**: exchange-rate results render without full page reload.
4. **User state**: session-based login and member-specific favorites.
5. **Database-backed product features**: recent search logs, favorites, board posts, image uploads, and comments.
6. **Repository cleanup**: converted tangled Notion notes into a structured GitHub-ready Maven project.
