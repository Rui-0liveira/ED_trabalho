package ed_trabalho;
/**
 * @author 8210191 Rodrigo Lopes
 * @author 8210322 Rui Oliveira
 */

/**
 * Enumeração MovEnum representa os tipos possíveis de movimentos para um bot no jogo.
 * Os tipos de movimentos disponíveis são SHORTESTPATH, RANDOMPATH, GREEDYPATH e DUMBPATH.
 */
public enum MovEnum {
    SHORTESTPATH, // Representa o movimento pelo caminho mais curto.
    RANDOMPATH, // Representa o movimento por um caminho aleatório.
    GREEDYPATH, // Representa o movimento por um caminho ganancioso.
    DUMBPATH // Representa o movimento por um caminho "burro" ou simples.
}
