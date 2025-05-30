create table tamanho_preco(
	id_tamanho_preco int8 primary key auto_increment,
	tamanho varchar(255) not null,
	valor float not null,
	id_produto int8 not null,
	constraint fk_tamanho_preco_produto foreign key (id_produto) references produto(id_produto)
);

alter table produto add column is_tamanho_unico bool;
alter table produto add column menor_valor float;
alter table produto modify column valor float;

alter table produto_carrinho add column id_tamanho_preco int8;
alter table produto_carrinho add constraint fk_produto_carrinho_tamanho_preco foreign key (id_tamanho_preco) references tamanho_preco(id_tamanho_preco);

alter table produto_pedido add column id_tamanho_preco int8;
alter table produto_pedido add column valor_momento_compra float;
alter table produto_pedido add column tamanho_momento_compra varchar(255);
alter table produto_pedido add constraint fk_produto_pedido_tamanho_preco foreign key (id_tamanho_preco) references tamanho_preco(id_tamanho_preco);

alter table produto add column ordenacao bigint;
update produto set ordenacao = id_produto where ordenacao is null;
alter table produto modify column ordenacao bigint not null;

alter table pedido add column zona_entrega varchar(50);