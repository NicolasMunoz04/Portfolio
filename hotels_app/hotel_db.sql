-- ========================
-- CLIENTES
-- ========================
create table if not exists public.cliente (
    cliente_id bigserial primary key,
    nombre text not null,
    email text unique not null,
    telefono text,
    fecha_registro timestamptz default now()
);

-- ========================
-- TIPOS DE HABITACIÓN (Catálogo)
-- ========================
create table if not exists public.tipo_habitacion (
    tipo_id bigserial primary key,
    nombre text not null unique, -- Ej: simple, doble, suite
    descripcion text
);

-- ========================
-- HABITACIONES
-- ========================
create table if not exists public.habitacion (
    habitacion_id bigserial primary key,
    numero text not null unique,
    tipo_id bigint not null references public.tipo_habitacion(tipo_id) on delete restrict,
    precio numeric(10,2) not null,
    estado text not null default 'disponible'
        check (estado in ('disponible','ocupada','mantenimiento'))
);

-- ========================
-- RESERVAS
-- ========================
create table if not exists public.reserva (
    reserva_id bigserial primary key,
    cliente_id bigint not null references public.cliente(cliente_id) on delete cascade,
    habitacion_id bigint not null references public.habitacion(habitacion_id) on delete cascade,
    fecha_inicio date not null,
    fecha_fin date not null,
    estado text not null default 'pendiente'
        check (estado in ('pendiente','confirmada','cancelada')),
    fecha_reserva timestamptz default now()
);

-- ========================
-- MÉTODOS DE PAGO (Catálogo)
-- ========================
create table if not exists public.metodo_pago (
    metodo_id bigserial primary key,
    nombre_metodo text not null unique -- Ej: tarjeta, efectivo, transferencia
);

-- ========================
-- PAGOS
-- ========================
create table if not exists public.pago (
    pago_id bigserial primary key,
    reserva_id bigint not null references public.reserva(reserva_id) on delete cascade,
    monto numeric(10,2) not null,
    metodo_id bigint not null references public.metodo_pago(metodo_id) on delete restrict,
    fecha_pago timestamptz default now()
);

-- ========================
-- CONVERSACIONES (Memoria de sesión)
-- ========================
create table if not exists public.conversacion (
    conversacion_id bigserial primary key,
    cliente_id bigint not null references public.cliente(cliente_id) on delete cascade,
    fecha_inicio timestamptz default now(),
    fecha_fin timestamptz,
    contexto jsonb default '{}'::jsonb -- memoria persistente (estado resumido)
);

-- ========================
-- MENSAJES (Historial de chat)
-- ========================
create table if not exists public.mensaje (
    mensaje_id bigserial primary key,
    conversacion_id bigint not null references public.conversacion(conversacion_id) on delete cascade,
    remitente text not null
        check (remitente in ('cliente','chatbot')),
    contenido text not null,
    datos jsonb, -- intenciones, payloads, botones, etc.
    fecha_envio timestamptz default now()
);