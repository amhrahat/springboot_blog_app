
-- Drop UNIQUE constraint on email
ALTER TABLE verification_tokens
DROP CONSTRAINT verification_tokens_email_key;

-- Add non-unique index for fast lookups
CREATE INDEX idx_verification_tokens_email
    ON verification_tokens(email);