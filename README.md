## API em Java + Spring Framework <img align="center" alt="Karen-Java" height="30" width="40" src="https://raw.githubusercontent.com/devicons/devicon/master/icons/java/java-original.svg"><img align="center" alt="Karen-spring" height="30" width="40" src="https://github.com/devicons/devicon/blob/master/icons/spring/spring-original.svg">

### O que este projeto faz?

- Recebe nome, profissão e idade do usuário, e realiza as 4 operações básicas: **Get, Post, Put e Delete**
- O banco de dados utilizado é o **PostgreSQL**, rodando via **Docker**
- Neste projeto, foi utilizado o Spring Framework, aplicando o **Spring Boot, Web, Data e Security**.
- Os testes unitários e de integração foram feitos utilizando **JUnit5 e Mockito**, e podem ser acessados através da pasta [```src/test/java/com/karenrodrigues/projetospring```](https://github.com/karenrodriguesx/ProjetoSpring/tree/main/src/test/java/com/karenrodrigues/projetospring)
- A documentação completa da API está disponível no **Swagger UI**

### Como rodar este projeto?

- Para carregar o banco de dados, é necessário instalar o Docker, e utilizar o comando ```docker-compose up```
- Após esse passo, basta acessar a classe ProjetoSpringApplication e iniciar a aplicação
- Pode ser necessário acessar o arquivo pom.xml, e rodar o Maven para que carregue todas as dependências

### Acessando o Swagger

- O Swagger é uma aplicação que auxilia na documentação de APIs, nesse caso, foi utilizado para documentar as funções da API, e por ele é possível testar todas as funções do sistema, sem a necessidade de utilizar o Postman/Insomnia para realizar requisições
- Para acessá-lo, basta rodar a aplicação e utilizar esse link no navegador http://localhost:8080/swagger-ui/index.html
- Será exibida a tela com todas as funções
- Para testar, basta clicar em *Try it out*, e digitar os dados solicitados
- Algumas funções podem solicitar autenticação, devido à implementação do **Spring Security 6** (Basic Auth), nesse caso, é necessário cadastrar um usuário e uma senha encriptada no banco de dados

