CREATE TABLE IF NOT EXISTS public.user_avatar
(
    id integer NOT NULL,
    data bytea,
    file_size bigint,
    media_type character varying(100) COLLATE pg_catalog."default",
    image character varying(150) COLLATE pg_catalog."default",
    CONSTRAINT user_avatar_pkey PRIMARY KEY (id),
    CONSTRAINT uk8jurrj6dimasmlyg4bn00s6sd UNIQUE (image),
    CONSTRAINT unique_image UNIQUE (image),
    CONSTRAINT fkpmb0gihcaxowv36orsv2cmxfn FOREIGN KEY (id)
        REFERENCES public.users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.user_avatar
    OWNER to postgres;