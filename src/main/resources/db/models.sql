CREATE TABLE IF NOT EXISTS public.models
(
    id integer NOT NULL,
    name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT models_pkey PRIMARY KEY (id),
    CONSTRAINT models_name_key UNIQUE (name)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.models
    OWNER to postgres;