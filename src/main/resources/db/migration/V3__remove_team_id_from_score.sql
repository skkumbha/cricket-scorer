-- sql
BEGIN;

-- 1) Drop any foreign-key or unique constraints on score.team_id (works even if constraint names vary)
DO $$
DECLARE
  c RECORD;
BEGIN
  FOR c IN
    SELECT con.conname AS conname, rel.relname AS relname, nsp.nspname AS nspname
    FROM pg_constraint con
    JOIN pg_class rel ON rel.oid = con.conrelid
    JOIN pg_namespace nsp ON nsp.oid = rel.relnamespace
    JOIN pg_attribute att ON att.attrelid = rel.oid AND att.attnum = ANY(con.conkey)
    WHERE rel.relname = 'score'
      AND att.attname = 'team_id'
      AND con.contype IN ('f','u')
  LOOP
    EXECUTE format('ALTER TABLE %I.%I DROP CONSTRAINT IF EXISTS %I', c.nspname, c.relname, c.conname);
  END LOOP;
END$$;

-- 2) Drop any indexes that include team_id (if created separately)
DO $$
DECLARE
  idx RECORD;
BEGIN
  FOR idx IN
    SELECT schemaname, indexname
    FROM pg_indexes
    WHERE tablename = 'score' AND indexdef ILIKE '%team_id%'
  LOOP
    EXECUTE format('DROP INDEX IF EXISTS %I.%I', idx.schemaname, idx.indexname);
  END LOOP;
END$$;

-- 3) Drop the column
ALTER TABLE public.score DROP COLUMN IF EXISTS team_id;

-- 4) Add new unique constraint without team_id
ALTER TABLE public.score
  ADD CONSTRAINT uq_score_match_innings UNIQUE (match_id, innings_id);

COMMIT;