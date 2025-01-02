CREATE TABLE IF NOT EXISTS public.devices
(
    brand_id integer,
    complect_id integer,
    model_id integer,
    year integer,
    type_id bigint,
    id character varying(255) COLLATE pg_catalog."default" NOT NULL,
    serialnumber character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT devices_pkey PRIMARY KEY (id),
    CONSTRAINT fk1308gatlnwa0h8vv6iqhuyk5u FOREIGN KEY (brand_id)
        REFERENCES public.brands (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fkcu93vd19dx038daepknudwsdi FOREIGN KEY (type_id)
        REFERENCES public.types (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fkhyxftl381wfoe63wrxlupgth4 FOREIGN KEY (complect_id)
        REFERENCES public.complects (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fkirc2ii289xtn4rf4hlaa3t0ul FOREIGN KEY (model_id)
        REFERENCES public.models (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.devices
    OWNER to postgres;