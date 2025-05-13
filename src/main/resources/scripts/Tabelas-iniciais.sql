create database mistura_boa_db;

-- drop table usuario;
-- drop table pessoa;
-- drop table categoria;
-- drop table produto;

create table pessoa (
	id_pessoa int8 primary key auto_increment,
	nm_pessoa varchar(255),
	cpf varchar(11) unique,
	telefone varchar(11),
	dt_exclusao timestamp,
	dt_nascimento date	
);


create table usuario (
	id_usuario int8 primary key auto_increment,
	email varchar(255) unique,
	senha varchar(255),
	role_usuario varchar(50),
	dt_exclusao timestamp,
	id_pessoa int8,
	constraint fk_pessoa_usuario foreign key (id_pessoa) references pessoa(id_pessoa)
);

create table categoria(
	id_categoria int8 primary key auto_increment,
	nm_categoria varchar(50) not null,
	ds_categoria varchar(255),
	icone_categoria text,
	numero varchar(10) not null,
	dt_exclusao timestamp
);

create table produto(
	id_produto int8 primary key auto_increment,
	nm_produto varchar(50) not null,
	ds_produto varchar(255) not null,
	img_produto longtext not null,
	valor varchar(25),
	numero_produto int not null,
	dt_exclusao timestamp,
	id_categoria int8 not null,
	constraint fk_categoria_produto foreign key (id_categoria) references categoria(id_categoria)
);

create table endereco(
	id_endereco int8 primary key auto_increment,
	nm_endereco varchar(255) not null,
	logradouro varchar(255) not null,
	bairro varchar(255) not null,
	complemento varchar(255),
	ponto_referencia varchar(255),
	numero int not null,
	id_usuario int8 not null,
	constraint fk_usuario_endereco foreign key (id_usuario) references usuario(id_usuario)
);

create table carrinho(
	id_carrinho int8 primary key auto_increment,
	valor_total decimal(10,2),
	id_usuario int8 not null,
	constraint fk_usuario_carrinho foreign key (id_usuario) references usuario(id_usuario)
);

create table produto_carrinho(
	id_produto_carrinho int8 primary key auto_increment,
	observacao varchar(255),
	id_produto int8 not null,
	id_carrinho int8 not null,
	constraint fk_produto_carrinho_produto foreign key (id_produto) references produto(id_produto),
	constraint fk_produto_carrinho_carrinho foreign key (id_carrinho) references carrinho(id_carrinho)
);


create table pedido(
	id_pedido int8 primary key auto_increment,
	numero_pedido int8 not null,
	valor_total decimal(10,2),
	status_pedido varchar(50),
	justificativa varchar(255),
	forma_pagamento varchar(50),
	precisa_troco bool,
	valor_troco decimal(10,2),
	dt_pedido timestamp not null,
	dt_fechamento_pedido timestamp,
	id_usuario int8 not null,
	id_endereco int8,
	constraint fk_usuario_pedido foreign key (id_usuario) references usuario(id_usuario),
	constraint fk_endereco_pedido foreign key (id_endereco) references endereco(id_endereco)
);

create table produto_pedido(
	id_produto_pedido int8 primary key auto_increment,
	observacao varchar(255),
	id_produto int8 not null,
	id_pedido int8 not null,
	constraint fk_produto_pedido_produto foreign key (id_produto) references produto(id_produto),
	constraint fk_produto_pedido_pedido foreign key (id_pedido) references pedido(id_pedido)
);