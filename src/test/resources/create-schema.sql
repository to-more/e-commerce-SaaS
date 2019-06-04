CREATE TABLE plans (
  id serial PRIMARY KEY,
  name varchar NOT NULL,
  fee numeric NOT NULL
);

INSERT into plans values (1, 'Basic', 2);
INSERT into plans values (2, 'Medium', 1);
INSERT into plans values (3, 'Full', 0.5);

CREATE TABLE merchants (
  id serial NOT NULL UNIQUE,
  name varchar NOT NULL,
  creation_date date NOT NULL,
  email varchar NOT NULL UNIQUE,
  phone  varchar NOT NULL UNIQUE,
  address varchar NOT NULL,
  balance numeric,
  credit numeric,
  plan_id int,
  FOREIGN KEY (plan_id) REFERENCES plans(id),
  PRIMARY KEY(phone, email)
);

CREATE TABLE sales (
  id serial PRIMARY KEY,
  creation_date date NOT NULL,
  product int NOT NULL,
  amount numeric NOT NULL,
  merchant_id int NOT NULL,
  CONSTRAINT fk_merchant_id FOREIGN KEY (merchant_id) REFERENCES merchants(id) ON DELETE CASCADE
);