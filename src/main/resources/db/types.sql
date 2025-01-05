CREATE TABLE IF NOT EXISTS public.types
(
    id bigint NOT NULL,
    name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    type character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT types_pkey PRIMARY KEY (id),
    CONSTRAINT types_name_key UNIQUE (name),
    CONSTRAINT types_type_key UNIQUE (type)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.types
    OWNER to postgres;