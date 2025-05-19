create table `pessoa` (
  `id_pessoa` bigint not null auto_increment,
  `nm_pessoa` varchar(255) default null,
  `cpf` varchar(11) default null,
  `telefone` varchar(11) default null,
  `dt_exclusao` timestamp null default null,
  `dt_nascimento` date default null,
  primary key (`id_pessoa`),
  unique key `cpf` (`cpf`)
) engine = InnoDB auto_increment = 3 default CHARSET = utf8mb4 collate = utf8mb4_0900_ai_ci;

create table `usuario` (
  `id_usuario` bigint not null auto_increment,
  `email` varchar(255) default null,
  `senha` varchar(255) default null,
  `role_usuario` varchar(50) default null,
  `dt_exclusao` timestamp null default null,
  `id_pessoa` bigint default null,
  primary key (`id_usuario`),
  unique key `email` (`email`),
  key `fk_pessoa_usuario` (`id_pessoa`),
  constraint `fk_pessoa_usuario` foreign key (`id_pessoa`) references `pessoa` (`id_pessoa`)
) engine = InnoDB auto_increment = 3 default CHARSET = utf8mb4 collate = utf8mb4_0900_ai_ci;

create table `categoria` (
  `id_categoria` bigint not null auto_increment,
  `nm_categoria` varchar(50) not null,
  `ds_categoria` varchar(255) default null,
  `icone_categoria` text,
  `dt_exclusao` timestamp null default null,
  primary key (`id_categoria`)
) engine = InnoDB auto_increment = 12 default CHARSET = utf8mb4 collate = utf8mb4_0900_ai_ci;

create table `produto` (
  `id_produto` bigint not null auto_increment,
  `nm_produto` varchar(50) not null,
  `ds_produto` varchar(255) not null,
  `img_produto` longtext,
  `valor` varchar(25) default null,
  `dt_exclusao` timestamp null default null,
  `id_categoria` bigint not null,
  primary key (`id_produto`),
  key `fk_categoria_produto` (`id_categoria`),
  constraint `fk_categoria_produto` foreign key (`id_categoria`) references `categoria` (`id_categoria`)
) engine = InnoDB auto_increment = 6 default CHARSET = utf8mb4 collate = utf8mb4_0900_ai_ci;

create table `carrinho` (
  `id_carrinho` bigint not null auto_increment,
  `valor_total` decimal(10,
2) default null,
  `id_usuario` bigint not null,
  primary key (`id_carrinho`),
  key `fk_usuario_carrinho` (`id_usuario`),
  constraint `fk_usuario_carrinho` foreign key (`id_usuario`) references `usuario` (`id_usuario`)
) engine = InnoDB auto_increment = 18 default CHARSET = utf8mb4 collate = utf8mb4_0900_ai_ci;

create table `produto_carrinho` (
  `id_produto_carrinho` bigint not null auto_increment,
  `observacao` varchar(255) default null,
  `id_produto` bigint not null,
  `id_carrinho` bigint not null,
  primary key (`id_produto_carrinho`),
  key `fk_produto_carrinho_produto` (`id_produto`),
  key `fk_produto_carrinho_carrinho` (`id_carrinho`),
  constraint `fk_produto_carrinho_carrinho` foreign key (`id_carrinho`) references `carrinho` (`id_carrinho`),
  constraint `fk_produto_carrinho_produto` foreign key (`id_produto`) references `produto` (`id_produto`)
) engine = InnoDB auto_increment = 29 default CHARSET = utf8mb4 collate = utf8mb4_0900_ai_ci;

create table `endereco` (
  `id_endereco` bigint not null auto_increment,
  `nm_endereco` varchar(255) not null,
  `logradouro` varchar(255) not null,
  `bairro` varchar(255) not null,
  `complemento` varchar(255) default null,
  `ponto_referencia` varchar(255) default null,
  `numero` int not null,
  `id_usuario` bigint not null,
  `dt_exclusao` timestamp null default null,
  primary key (`id_endereco`),
  key `fk_usuario_endereco` (`id_usuario`),
  constraint `fk_usuario_endereco` foreign key (`id_usuario`) references `usuario` (`id_usuario`)
) engine = InnoDB auto_increment = 5 default CHARSET = utf8mb4 collate = utf8mb4_0900_ai_ci;

create table `pedido` (
  `id_pedido` bigint not null auto_increment,
  `numero_pedido` bigint not null,
  `valor_total` decimal(10,
2) default null,
  `status_pedido` varchar(50) default null,
  `justificativa` varchar(255) default null,
  `forma_pagamento` varchar(50) default null,
  `precisa_troco` tinyint(1) default null,
  `valor_troco` decimal(10,
2) default null,
  `dt_pedido` timestamp not null,
  `dt_fechamento_pedido` timestamp null default null,
  `id_usuario` bigint not null,
  `id_endereco` bigint default null,
  primary key (`id_pedido`),
  key `fk_usuario_pedido` (`id_usuario`),
  key `fk_endereco_pedido` (`id_endereco`),
  constraint `fk_endereco_pedido` foreign key (`id_endereco`) references `endereco` (`id_endereco`),
  constraint `fk_usuario_pedido` foreign key (`id_usuario`) references `usuario` (`id_usuario`)
) engine = InnoDB auto_increment = 10 default CHARSET = utf8mb4 collate = utf8mb4_0900_ai_ci;

create table `produto_pedido` (
  `id_produto_pedido` bigint not null auto_increment,
  `observacao` varchar(255) default null,
  `id_produto` bigint not null,
  `id_pedido` bigint not null,
  primary key (`id_produto_pedido`),
  key `fk_produto_pedido_produto` (`id_produto`),
  key `fk_produto_pedido_pedido` (`id_pedido`),
  constraint `fk_produto_pedido_pedido` foreign key (`id_pedido`) references `pedido` (`id_pedido`),
  constraint `fk_produto_pedido_produto` foreign key (`id_produto`) references `produto` (`id_produto`)
) engine = InnoDB auto_increment = 12 default CHARSET = utf8mb4 collate = utf8mb4_0900_ai_ci;


