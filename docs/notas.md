#Logar no prompt de comando:
mysql -u root -p senha_atual


# Trocar a senha do usuário root na base de dados:
# MYSQL Versão 5.7 pra cima
```
ALTER USER user IDENTIFIED BY 'auth_string'; 
flush privileges;
```

#MYSQL Versão 5.6 para baixo
```
update mysql.user set password=PASSWORD('nova_senha') where user='root'
flush privileges;
```


# Criar base de dados:
```
create database pensaodb;
```


# script para banco de dados:

CREATE TABLE `pensaodb`.`pensao` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nomemulher` VARCHAR(100) NOT NULL,
  `nomefilho` VARCHAR(100) NOT NULL,
  `valorpensao` FLOAT NOT NULL,
  PRIMARY KEY (`id`));


# script para carga de massa de teste:
insert into pensao values(null, "Ivania Linda Marins", "Lucas Gregorio Santos", 1200.00);
insert into pensao values(null, "Ivania Linda Marins", "Larrisa Gregorio Santos", 1100.00);
insert into pensao values(null, "Silvana Moraes Katchinbi", "Yuri Morais Gregorio Santos", 937.27);





