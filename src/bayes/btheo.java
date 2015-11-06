package bayes;

import java.util.*;

class btheo {

    static char outlook[] =     {'S', 'S', 'O', 'R', 'R', 'R', 'O', 'S', 'S', 'R', 'S', 'O', 'O', 'R'};
    static char temperature[] = {'H', 'H', 'H', 'M', 'C', 'C', 'C', 'M', 'C', 'M', 'M', 'M', 'H', 'M'};
    static char humidity[] =    {'P', 'P', 'P', 'P', 'N', 'N', 'N', 'P', 'N', 'N', 'N', 'P', 'N', 'P'};
    static char windy[] =       {'F', 'T', 'F', 'F', 'F', 'T', 'T', 'F', 'F', 'F', 'T', 'T', 'F', 'T'};
    static char class1[] =      {'N', 'N', 'P', 'P', 'P', 'N', 'P', 'N', 'P', 'P', 'P', 'P', 'P', 'N'};

    static double prob[][] = new double[4][2];

    static double pp = 9.0 / 14.0;
    static double npp = 5.0 / 14.0;

    static int flag = 0;
    static int flag1 = 0;

    static double play_N = 1;
    static double notplay_N = 1;

    static void cal_N(int a) {
        if (a == 1) {
            for (int i = 0; i < 4; ++i) {
                play_N *= prob[i][0];
            }

            play_N *= pp;
            //System.out.println("\nValue of N of play \n"+play_N);
        } else {
            for (int i = 0; i < 4; ++i) {
                notplay_N *= prob[i][1];
            }

            notplay_N *= npp;
            //System.out.println("\nValue of N of No play \n"+notplay_N);
        }
    }

    static double cal_play_prob(char ch) {
        double prob = 0;
        double count = 0;

        if (flag == 0) {
            for (int i = 0; i < 14; ++i) {
                if (outlook[i] == ch && class1[i] == 'P') {
                    ++count;
                }
            }

            prob = count / 9.0;

            flag = 1;
        } else if (flag == 1) {
            for (int i = 0; i < 14; ++i) {
                if (temperature[i] == ch && class1[i] == 'P') {
                    ++count;
                }
            }

            prob = count / 9.0;

            flag = 2;
        } else if (flag == 2) {
            for (int i = 0; i < 14; ++i) {
                if (humidity[i] == ch && class1[i] == 'P') {
                    ++count;
                }
            }

            prob = count / 9.0;

            flag = 3;
        } else if (flag == 3) {
            for (int i = 0; i < 14; ++i) {
                if (windy[i] == ch && class1[i] == 'P') {
                    ++count;
                }
            }

            prob = count / 9.0;
        }
        return prob;
    }

    static double cal_noplay_prob(char ch) {
        double prob = 0;
        double count = 0;

        if (flag1 == 0) {
            for (int i = 0; i < 14; ++i) {
                if (outlook[i] == ch && class1[i] == 'N') {
                    ++count;
                }
            }

            prob = count / 5.0;

            flag1 = 1;
        } else if (flag1 == 1) {
            for (int i = 0; i < 14; ++i) {
                if (temperature[i] == ch && class1[i] == 'N') {
                    ++count;
                }
            }

            prob = count / 5.0;

            flag1 = 2;
        } else if (flag1 == 2) {
            for (int i = 0; i < 14; ++i) {
                if (humidity[i] == ch && class1[i] == 'N') {
                    ++count;
                }
            }

            prob = count / 5.0;

            flag1 = 3;
        } else if (flag1 == 3) {
            for (int i = 0; i < 14; ++i) {
                if (windy[i] == ch && class1[i] == 'N') {
                    ++count;
                }
            }

            prob = count / 5.0;
        }
        return prob;
    }

    public static void main(String args[]) {
        Scanner scr = new Scanner(System.in);
        System.out.println("Table\n");
        System.out.println("Outlook\t   Temperature\t    Humidity\t      Windy     \tClass");
        for (int i = 0; i < 14; ++i) {
            System.out.print(outlook[i] + "\t\t" + temperature[i] + "\t\t" + humidity[i] + "\t\t" + windy[i] + "\t\t" + class1[i]);
            System.out.println();
        }
        System.out.println("Menu:\nOutlook: Sunny=S Overcast=O Rain=R\tTemperature: Hot=H Mild=M Cool=C\n");
        System.out.println("Humidity: Peak=P Normal=N\t\tWindy: True=T False=F\n\nYour input should belong to one of these classes.\n");
        System.out.println("class1: Play=P   class2:Not Play=NP");
        System.out.println("\nEnter your input: example. t={rain,hot,peak,false} input will be R,H,P,F");
        String s = scr.nextLine();
        char ch;
        int count = 0;
        for (int i = 0; i < 8; i += 2) {
            ch = s.charAt(i);
            prob[count][0] = cal_play_prob(ch);
            prob[count][1] = cal_noplay_prob(ch);
            ++count;
        }

        cal_N(1);
        cal_N(2);

        double pt = play_N + notplay_N;

        double prob_of_play = 0;
        double prob_of_noplay = 0;

        prob_of_play = play_N / pt;
        prob_of_noplay = notplay_N / pt;

        System.out.println("\nProbability of play " + prob_of_play);
        System.out.println("\nProbability of NO play " + prob_of_noplay);

        if (prob_of_play > prob_of_noplay) {
            System.out.println("\nThe new tuple classified under \"PLAY\" category.Hence there will be play!!!");
        } else {
            System.out.println("\nThe new tuple classified under \"NO PLAY\" category.Hence there will be NO play.");
        }

    }
}
