CREATE TABLE IF NOT EXISTS public.complects
(
    id integer NOT NULL,
    CONSTRAINT complects_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.complects
    OWNER to postgres;