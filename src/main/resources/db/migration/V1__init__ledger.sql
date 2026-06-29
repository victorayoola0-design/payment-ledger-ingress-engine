CREATE TABLE accounts (
                          id UUID PRIMARY KEY,
                          user_id UUID NOT NULL,
                          currency VARCHAR(3) NOT NULL,
                          balance NUMERIC(18, 4) NOT NULL DEFAULT 0.0000,
                          version BIGINT NOT NULL,
                          created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE transfers (
                           id UUID PRIMARY KEY,git
                           user_id UUID NOT NULL,
                           source_currency VARCHAR(3) NOT NULL,
                           target_currency VARCHAR(3) NOT NULL,
                           source_amount NUMERIC(18, 4) NOT NULL,
                           target_amount NUMERIC(18, 4) NOT NULL,
                           fx_rate NUMERIC(12, 6) NOT NULL,
                           status VARCHAR(30) NOT NULL,
                           idempotency_key VARCHAR(255) UNIQUE NOT NULL,
                           recipient_routing_details JSONB NOT NULL,
                           updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                           created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE ledger_entries (
                                id UUID PRIMARY KEY,
                                account_id UUID REFERENCES accounts(id),
                                transfer_id UUID REFERENCES transfers(id),
                                amount NUMERIC(18, 4) NOT NULL,
                                type VARCHAR(30) NOT NULL,
                                created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_accounts_user ON accounts(user_id);
CREATE INDEX idx_transfers_idempotency ON transfers(idempotency_key);
CREATE INDEX idx_ledger_account ON ledger_entries(account_id);