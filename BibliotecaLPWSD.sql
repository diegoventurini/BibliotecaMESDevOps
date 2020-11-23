-- MySQL Workbench Synchronization
-- Generated: 2019-05-09 17:35
-- Model: New Model
-- Version: 1.0
-- Project: Name of the project
-- Author: Daniel Meireles

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

CREATE SCHEMA IF NOT EXISTS `BibliotecaLPWSD` DEFAULT CHARACTER SET utf8 ;

CREATE TABLE IF NOT EXISTS `BibliotecaLPWSD`.`Livro` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `titulo` VARCHAR(100) NOT NULL,
  `isbn` VARCHAR(9) NOT NULL,
  `edicao` INT(11) NOT NULL,
  `ano` INT(5) NOT NULL,
  `capa` VARCHAR(45) NULL DEFAULT NULL,
  `arquivo` VARCHAR(45) NULL DEFAULT NULL,
  `idEditora` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_tbLivro_tbEditora1_idx` (`idEditora` ASC) ,
  UNIQUE INDEX `titulo_UNIQUE` (`titulo` ASC) ,
  CONSTRAINT `fk_tbLivro_tbEditora1`
    FOREIGN KEY (`idEditora`)
    REFERENCES `BibliotecaLPWSD`.`Editora` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `BibliotecaLPWSD`.`Assunto` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `assunto` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `assunto_UNIQUE` (`assunto` ASC) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `BibliotecaLPWSD`.`Exemplar` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `circular` TINYINT(1) NULL DEFAULT NULL,
  `idLivro` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_tbExemplar_tbLivro1_idx` (`idLivro` ASC) ,
  CONSTRAINT `fk_tbExemplar_tbLivro1`
    FOREIGN KEY (`idLivro`)
    REFERENCES `BibliotecaLPWSD`.`Livro` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `BibliotecaLPWSD`.`Emprestimo` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `dataEmprestimo` DATE NOT NULL,
  `dataDevolucaoPrevista` DATE NOT NULL,
  `dataDevolucao` DATE NULL DEFAULT NULL,
  `idExemplar` INT(11) NOT NULL,
  `idUsuario` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_tbEmprestimo_tbExemplar1_idx` (`idExemplar` ASC) ,
  INDEX `fk_tbEmprestimo_tbUsuario1_idx` (`idUsuario` ASC) ,
  CONSTRAINT `fk_tbEmprestimo_tbExemplar1`
    FOREIGN KEY (`idExemplar`)
    REFERENCES `BibliotecaLPWSD`.`Exemplar` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_tbEmprestimo_tbUsuario1`
    FOREIGN KEY (`idUsuario`)
    REFERENCES `BibliotecaLPWSD`.`Usuario` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `BibliotecaLPWSD`.`Usuario` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(100) NOT NULL,
  `tipo` CHAR(1) NOT NULL,
  `email` VARCHAR(100) NOT NULL,
  `usuario` VARCHAR(45) NOT NULL,
  `senha` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `usuario_UNIQUE` (`usuario` ASC) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `BibliotecaLPWSD`.`Editora` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `nome_UNIQUE` (`nome` ASC) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `BibliotecaLPWSD`.`Autor` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `nome_UNIQUE` (`nome` ASC) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `BibliotecaLPWSD`.`AutorLivro` (
  `idLivro` INT(11) NOT NULL,
  `idAutor` INT(11) NOT NULL,
  PRIMARY KEY (`idLivro`, `idAutor`),
  INDEX `fk_tbLivro_has_tbAutores_tbAutores1_idx` (`idAutor` ASC) ,
  INDEX `fk_tbLivro_has_tbAutores_tbLivro1_idx` (`idLivro` ASC) ,
  CONSTRAINT `fk_tbLivro_has_tbAutores_tbLivro1`
    FOREIGN KEY (`idLivro`)
    REFERENCES `BibliotecaLPWSD`.`Livro` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_tbLivro_has_tbAutores_tbAutores1`
    FOREIGN KEY (`idAutor`)
    REFERENCES `BibliotecaLPWSD`.`Autor` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `BibliotecaLPWSD`.`LivroAssunto` (
  `idLivro` INT(11) NOT NULL,
  `idAssunto` INT(11) NOT NULL,
  PRIMARY KEY (`idLivro`, `idAssunto`),
  INDEX `fk_Livro_has_Assunto_Assunto1_idx` (`idAssunto` ASC) ,
  INDEX `fk_Livro_has_Assunto_Livro1_idx` (`idLivro` ASC) ,
  CONSTRAINT `fk_Livro_has_Assunto_Livro1`
    FOREIGN KEY (`idLivro`)
    REFERENCES `BibliotecaLPWSD`.`Livro` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Livro_has_Assunto_Assunto1`
    FOREIGN KEY (`idAssunto`)
    REFERENCES `BibliotecaLPWSD`.`Assunto` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `BibliotecaLPWSD`.`Reserva` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `dataReserva` DATE NOT NULL,
  `dataDevolucaoPrevista` DATE NOT NULL,
  `cancelada` TINYINT(1) NULL DEFAULT NULL,
  `obsCancelamento` VARCHAR(45) NULL DEFAULT NULL,
  `idExemplar` INT(11) NOT NULL,
  `idUsuario` INT(11) NOT NULL,
  `idEmprestimo` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Reserva_Exemplar1_idx` (`idExemplar` ASC) ,
  INDEX `fk_Reserva_Usuario1_idx` (`idUsuario` ASC) ,
  INDEX `fk_Reserva_Emprestimo1_idx` (`idEmprestimo` ASC) ,
  CONSTRAINT `fk_Reserva_Exemplar1`
    FOREIGN KEY (`idExemplar`)
    REFERENCES `BibliotecaLPWSD`.`Exemplar` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Reserva_Usuario1`
    FOREIGN KEY (`idUsuario`)
    REFERENCES `BibliotecaLPWSD`.`Usuario` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Reserva_Emprestimo1`
    FOREIGN KEY (`idEmprestimo`)
    REFERENCES `BibliotecaLPWSD`.`Emprestimo` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

USE BibliotecaLPWSD;
INSERT INTO Usuario (nome, tipo, email, usuario, senha) VALUES ('Administrador', '5', 'admin@admin.com', 'admin', 'admin');
COMMIT;