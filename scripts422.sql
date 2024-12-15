CREATE TABLE model (
    id SERIAL,
    name VARCHAR(50),
    price NUMERIC,
);

CREATE TABLE brand (
    id SERIAL,
    name VARCHAR(50),
    model_id INTEGER REFERENCES model (id),
);

CREATE TABLE license (
    id SERIAL,
    number INTEGER,
);

CREATE TABLE person (
    id SERIAL,
    name VARCHAR(50),
    age SMALLINT,
    license_id INTEGER REFERENCES license (id),
);

CREATE TABLE person_brand_connector (
    id SERIAL,
    person_id INTEGER REFERENCES person (id),
    brand_id INTEGER REFERENCES brand (id),
);