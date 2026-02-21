-- 1) Drop FK/unique constraints that reference match_id or team_id (names can vary)
DO $$
DECLARE
  c RECORD;
BEGIN
  FOR c IN
    SELECT con.conname, nsp.nspname, rel.relname
    FROM pg_constraint con
    JOIN pg_class rel ON rel.oid = con.conrelid
    JOIN pg_namespace nsp ON nsp.oid = rel.relnamespace
    JOIN pg_attribute att ON att.attrelid = rel.oid AND att.attnum = ANY(con.conkey)
    WHERE nsp.nspname = 'public'
      AND rel.relname = 'player_score'
      AND att.attname IN ('team_id','match_id')
      AND con.contype IN ('f','u')
  LOOP
    EXECUTE format('ALTER TABLE %I.%I DROP CONSTRAINT IF EXISTS %I', c.nspname, c.relname, c.conname);
  END LOOP;
END$$;

-- 2) Drop indexes that include match_id or team_id (if created separately)
DO $$
DECLARE
  idx RECORD;
BEGIN
  FOR idx IN
    SELECT schemaname, indexname
    FROM pg_indexes
    WHERE schemaname = 'public'
      AND tablename = 'player_score'
      AND (indexdef ILIKE '%team_id%' OR indexdef ILIKE '%match_id%')
  LOOP
    EXECUTE format('DROP INDEX IF EXISTS %I.%I', idx.schemaname, idx.indexname);
  END LOOP;
END$$;

-- 3) Drop columns
ALTER TABLE public.player_score
  DROP COLUMN IF EXISTS team_id,
  DROP COLUMN IF EXISTS match_id;

-- 4) Ensure target unique constraint doesn't already exist (dev re-runs)
ALTER TABLE public.player_score
  DROP CONSTRAINT IF EXISTS uk_player_score_innings_player;

-- 5) Add new unique constraint
ALTER TABLE public.player_score
  ADD CONSTRAINT uk_player_score_innings_player UNIQUE (innings_id, player_id);