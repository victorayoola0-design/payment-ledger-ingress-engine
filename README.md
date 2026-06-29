# Payment Processing Engine

An enterprise-grade, event-driven financial ingestion sub-ledger engineered for high-throughput payment orchestration, strict ACID-compliant double-entry accounting, and low-latency state tracking.

---

## 1. Architectural Blueprint & Core Design

This system leverages **Domain-Driven Design (DDD)** patterns to enforce absolute separation of concerns between external infrastructure protocols (Kafka, Redis, Web) and core financial domain invariants.

### Distributed Data Flow Topology

### Strategic Infrastructure Selections

* **Ingress Stream Ingestion (Apache Kafka 4.2):** Decouples incoming financial requests from execution pools. Consumes transaction messages over the `payment.transfer.intents` topic with built-in transactional listener boundaries.
* **Low-Latency Distributed Locking (Redis 7.5):** Acts as a high-speed lock arbitrator via thread-safe lettuce bindings to enforce cryptographic transaction idempotency before database compute allocation.
* **System of Record Ledger (PostgreSQL 18.3):** Serves as the ultimate immutable source of truth. Enforces relational integrity constraints for true financial reconciliation.

---

## 2. Production-Grade Enterprise Stack

The platform is anchored to stabilized, long-term support ecosystem dependencies:

| Tier | Component Framework | Active Production Version | Engineering Purpose |
| :--- | :--- | :--- | :--- |
| **Runtime VM** | Eclipse Temurin JDK | `25.0.2` | Advanced baseline execution space utilizing optimized modern garbage collection. |
| **Application Framework** | Spring Boot | `4.1.0` | Inversion of control, unified bean management, and boot orchestration. |
| **Persistence Engine** | Hibernate Core / JPA | `7.4.1.Final` | Advanced object-relational mapping bound to strict data contexts. |
| **Schema Evolution** | Flyway DB Core | `12.4.0` | Automated, auditable, and versioned database migration management. |
| **Connection Pooling** | HikariCP | `7.0.2` | Ultra-low overhead connection layer for steady database connection health. |
| **Cache Client** | Lettuce Core | `7.5.2.RELEASE` | Non-blocking reactive communication with the distributed caching layer. |

---

## 3. Financial Integrity & Concurrency Constraints

Operating inside global fintech infrastructures mandates zero-tolerance boundaries for data corruption, race conditions, or loss. The engine enforces safety via three major patterns:

### 1. Multi-Store Context Isolation
To prevent context bleeding and state pollution, data access interfaces are strictly separated:
* `SpringDataAccountRepository` and `SpringDataLedgerRepository` operate explicitly within the relational JPA boundary.
* Redis structures are managed via decoupled template clients, preventing mixed entity scopes.

### 2. Idempotency Gatekeeping
Every incoming instruction must pass a unique verification sequence. Non-blocking atomic validation keys are checked inside Redis upon entry to guarantee that a network retry never executes a duplicate settlement request.

### 3. Derived Balances via Double-Entry Bookkeeping
To minimize database lock times and prevent row contention on high-frequency account modifications, current balances are derived mathematically by aggregating entries rather than mutating a single static cell:

$$\text{Current Available Balance} = \sum(\text{Credits}) - \sum(\text{Debits})$$

---

## 4. Local Infrastructure Provisioning

The development loop is fully containerized, mirroring production topologies locally. Local runtime services are defined in `compose.yaml` with dedicated diagnostic health probes to guarantee consistent boot ordering:

### Spin Up the Infrastructure
Execute the following command from the project root to run the multi-node engine background services:
```bash
 docker compose up -d

docker compose ps

./mvnw clean test

./mvnw clean package

