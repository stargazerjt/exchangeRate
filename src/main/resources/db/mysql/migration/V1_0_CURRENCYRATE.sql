create table IF NOT EXISTS  world.currency_rate(
	currency_code varchar(3)not null,
    exchange_rate double(20,8)not null,
    created_date datetime(6) not null,
    primary key(currency_code));