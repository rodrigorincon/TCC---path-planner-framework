# Traveller, o framework de definição de trajetória
O Traveller é um framework para definição de trajetória (path planner) para robótica móvel. Facilite seu desenvolvimento usando uma solução pronta para criar percursos e concentre-se no real diferencial do seu robô!

#O que o Traveller fornece
O framework permite definir um percurso livre de obstáculos entre dois pontos para o robô percorrer sem risco de colisão. O Traveller considera a região como um mapa 2d e o robô como um círculo de diâmetro igual a maior diagonal da máquina. Os algoritmos utilizados no framework são algoritmos globais de definição de trajetória.

Para tanto, o Traveller deve receber de entrada: os pontos inicial e final (em coordenadas x e y), o mapa (como uma matriz de booleanos), os algoritmos que desejam executar (algoritmo de path planner e de melhor caminho, em forma de String), a largura do robô, a largura que cada célula da matriz representa (na mesma unidade de medida que a largura do robô), o valor a ser considerado livre no mapa (True ou False) e se os obstáculos devem ser expandidos ou não. Como retorno, o framework retorna uma lista de pontos que o robô deve seguir e o tamanho total do percurso.

#Como o Traveller funciona
O framework possui dois módulos principais: path planner (definição de trajetória) e best path (melhor caminho). O módulo path planner recebe um mapa do ambiente (em forma de matriz de inteiros, que é internamente convertido da matriz de booleanos recebido pelo framework) e, através do algoritmo escolhido, cria um grafo de regiões livres. Após gerar o grafo é executado o módulo best path, que define a lista de pontos a partir do grafo. Vale ressaltar que o algoritmo de best path pode ser tanto um algoritmo de menor caminho (Djikstra, A*), menor curvatura, menor gasto de bateria, entre outros. Como dito antes, os algoritmos a serem utilizados devem ser informados em forma de String.

O framework fornece uma arquitetura flexível para a fácil troca entre os algoritmos em tempo de execução. É possível ainda implementar seus próprios algoritmos de definição de trajetória e de melhor caminho, herdando das classes abstratas. Isso permite utilizar qualquer algoritmo sem precisar alterar no resto do framework, dando os métodos a serem implementados, as entradas e saídas a serem trabalhadas, além de permitir a comparação entre os diferentes algoritmos.

#Como instalar
O Traveller funciona em um servidor externo que se comunica com o robô via bluetooth, assim o principal requisito é possuir uma máquina com bluetooth habilitado próxima a região onde o robô trabalhará.

O Traveller é feito em linguagem Java, sendo assim necessário ainda a instalação do JDK 7 no servidor. O código no robô que repassa os dados ao servidor e recebe a resposta também é feito em Java e o robô deve possuir uma JVM instalada.

Para a comunicação, pareie pelo menos uma vez o robô com o servidor para que haja na memória do robô um registro do mesmo. O servidor utiliza a biblioteca Bluecove 2.1.1 (disponível dentro da pasta server/bin) e a mesma deve ser adicionada ao claspath do projeto como biblioteca externa.
