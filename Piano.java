import java.util.Scanner;
public class Piano {
    public static void main(String[] args){
        Scanner entrada = new Scanner(System.in);
        System.out.println("Digite a quantida de teclas e acordes:");
        int teclas = entrada.nextInt();
        int acordes = entrada.nextInt();

        monta(0, 0, teclas-1);

        System.out.println("Digite um acorde:");

        while (acordes-- > 0) {
            nota1 = entrada.nextInt();
            nota2 = entrada.nextInt();

            contagem(0, 0, teclas-1);

            valor = 0;
            for (int i = 0; i < NOTAS; ++i) {
                if (cont[i] >= cont[valor]) valor = i;
            }

            espera(0, 0, teclas-1);
        }

        for (nota1 = 0; nota1 < teclas; ++nota1) {
            nota2 = nota1;
            contagem(0, 0, teclas-1);
            valor = 0;
            for (int i = 0; i < NOTAS; ++i) {
                if (cont[i] >= cont[valor]) valor = i;
            }
            System.out.println(valor);
        }
    }

    static final int BT = 1 << 18;
    static final int NOTAS = 9;

    static int[] [] func = new int[BT][NOTAS];
    static int[] troca = new int[BT];

    static void monta(int base, int m, int M) {
        troca[base] = 0;

        if (m == M){
            func[base][1] = 1;
        } else {
            int lft = (base << 1) + 1;
            int rgt = lft + 1;
            int frt = (m + M) >> 1;

            monta(lft, m, frt);
            monta(rgt, frt+1, M);

            func[base][1] = M-m+1;
        }
    }

    static int[] vetAux = new int[NOTAS];
    static void vet(int[] v, int r){
        r = NOTAS - r;
        int j = 0;
        for (int i = r; i < NOTAS; ++i){
            vetAux[j++] = v[i];
        }
        for (int i = 0; i < r; ++i){
            vetAux[j++] = v[i];
        }
        System.arraycopy(vetAux, 0, v, 0, NOTAS);
    }

    static void appTroca(int base, boolean ok){
        if (troca[base] == 0) return;

        if (!ok){
            int lft = (base << 1) + 1;
            int rgt = lft + 1;
            troca[lft] = (troca[lft] + troca[base]) % NOTAS;
            troca[rgt] = (troca[rgt] + troca[base]) % NOTAS;
        }
        vet(func[base],troca[base]);
        troca[base] = 0;
    }

    static int nota1, nota2, valor;
    static int[] cont = new int[NOTAS];

    static void contagem(int base, int m, int M){
        appTroca(base, m == M);
        if (nota1 <= m && M <= nota2){
            if (m == nota1) {
                System.arraycopy(func[base], 0, cont, 0, NOTAS);
            } else {
                for (int i = 0; i < NOTAS; ++i) cont[i] += func[base][i];
            }
        } else {
            int lft = (base << 1) + 1;
            int rgt = lft + 1;
            int frt = (m + M) >> 1;

            if (frt >= nota1) contagem(lft, m, frt);
            if (frt+1 <= nota2) contagem(rgt, frt+1, M);
        }
    }

    static void espera(int base, int m, int M){
        appTroca(base, m == M);

        if (M < nota1) return;
        if (m > nota2) return;
        if (nota1 <= m && M <= nota2){
            troca[base] = valor;
            appTroca(base, m == M);
        } else{
            int lft = (base << 1) + 1;
            int rgt = lft + 1;
            int frt = (m + M) >> 1;

            espera(lft, m, frt);
            espera(rgt, frt+1, M);

            for (int i = 0; i < NOTAS; ++i)
                func[base][i] = func[lft][i] + func[rgt][i];
        }
    }
}
