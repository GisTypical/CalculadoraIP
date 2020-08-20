CREATE TABLE public.ipv4
(
    ip character varying(20) NOT NULL,
    seguridad character varying(20)",
    clase character varying(2),
    apipa character varying(2),
    reservada character varying(50),
    envioinfo character varying(20),
    dirred character varying(20),
    gateway character varying(20),
    broadcast character varying(20),
    rango character varying(50),
    CONSTRAINT "IPv4_pkey" PRIMARY KEY (ip)
)
