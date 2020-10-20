package com.company;

import java.util.Locale;
import java.util.Scanner;

public class Main {
    static byte[] data = new byte[64];
    static byte count2 = 0;

    public Main() {
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        byte dataType = 0;
        double d = 0.0D;
        long l = 0L;
        byte pow = 0;
        byte count = 0;
        System.out.println("Введите число:");

        try {
            l = sc.nextLong();
        } catch (Exception var14) {
            try {
                d = sc.nextDouble();
            } catch (Exception var13) {
                try {
                    sc.useLocale(Locale.US);
                    d = sc.nextDouble();
                } catch (Exception var12) {
                    System.err.println("Некоррекно введено число");
                }
            }
        }
        if (d == (1.0 / 0)) {
            System.out.println("0111 1111 1111 0000 0000 0000 0000");
        } else if (d == (-1.0 / 0)) {
            System.out.println("1111 1111 1111 0000 0000 0000 0000");
        } else {
            System.out.println("Какой тип данных вам нужен?(введите цифру):");
            System.out.println("1)Double");
            if (d != 0.0D) {
                if (d < 3.4D * Math.pow(10.0D, 38.0D) && d > -3.4D * Math.pow(10.0D, 38.0D)) {
                    System.out.println("2)Float");
                }
                dataType = sc.nextByte();
                if (dataType == 1) {
                    pow = 64;
                } else {
                    pow = 32;
                }

                ConvertPoint(dataType, d);
            } else {
                System.out.println("2)Long");
                if (d < 3.4D * Math.pow(10.0D, 38.0D) && d > -3.4D * Math.pow(10.0D, 38.0D)) {
                    System.out.println("3)Float");
                }
                if (l < 2147483647L && l > -2147483648L) {
                    System.out.println("4)Int");
                }

                if (l < 32767L && l > -32768L) {
                    System.out.println("5)Short");
                }

                if (l < 127L && l > -128L) {
                    System.out.println("6)Byte");
                }
                try {
                    dataType = sc.nextByte();
                } catch (Exception exception) {
                    System.err.println("Некорректно выбран тип данных");
                }
                sc.close();
                switch (dataType) {
                    case 1:
                        pow = 64;
                        ConvertPoint((byte) 1, (double) l);
                        break;
                    case 2:
                        pow = 64;
                        Convert(pow, l);
                        break;
                    case 3:
                        pow = 32;
                        ConvertPoint((byte) 2, (double) l);
                        break;
                    case 4:
                        pow = 32;
                        Convert(pow, l);
                        break;
                    case 5:
                        pow = 16;
                        Convert(pow, l);
                        break;
                    case 6:
                        pow = 8;
                        Convert(pow, l);
                    default:

                }
            }
            System.out.println("Ваше число:");
            for (byte i = (byte) (64 - pow); i < 64; ++i) {
                if (count == 4) {
                    count = 0;
                    System.out.print(" ");
                }

                System.out.print(data[i]);
                ++count;
            }

        }
    }

    static void Convert(byte pow, long b) {
        count2 = (byte) (64 - pow);
        byte i;
        if (b > 0L) {
            ++count2;

            for (i = (byte) (pow - 2); i >= 0; --i) {
                data[count2] = (byte) ((int) (b / (long) Math.pow(2.0D, (double) i)));
                b %= (long) Math.pow(2.0D, (double) i);
                ++count2;
            }
        } else if (b < 0L) {
            for (i = (byte) (pow - 1); i >= 0; --i) {
                data[count2] = (byte) ((double) b / Math.pow(-2.0D, (double) i) == 0.0D ? 1 : 0);
                b %= (long) Math.pow(-2.0D, (double) i);
                ++count2;
            }

            for (i = 63; i > -1; --i) {
                if (data[i] == 0) {
                    data[i] = 1;
                    break;
                }

                data[i] = 0;
            }
        }

    }

    static void ConvertPoint(byte pow, double b) {
        boolean bool1 = false;
        boolean bool2 = false;
        byte count2MemStart = 0;
        byte count3MemStart = 0;
        byte count3 = 0;
        byte[] data1 = new byte[120];
        short exponent;
        byte Pow;
        short i;
        if (b != 0) {
            if (pow == 1) {
                if (b > 0.0D) {
                    data[count2] = 0;
                } else {
                    data[count2] = 1;
                    b *= -1.0D;
                }

                ++count2;

                for (Pow = 51; (b != 0.0D || Pow > -2) && count3 != 120; ++count3) {
                    data1[count3] = (byte) ((int) (b / Math.pow(2.0D, (double) Pow)));
                    if (!bool1 && data1[count3] == 1) {
                        count2MemStart = (byte) (count3 + 1);
                        bool1 = true;
                    }

                    if (!bool2 && Pow < 0) {
                        count3MemStart = count3;
                        bool2 = true;
                    }

                    b %= Math.pow(2.0D, (double) Pow);
                    --Pow;
                }

                exponent = (short) ((int) ((double) (count3MemStart - count2MemStart) + Math.pow(2.0D, 10.0D) - 1.0D));

                for (i = 10; i >= 0; --i) {
                    data[count2] = (byte) ((int) ((double) exponent / Math.pow(2.0D, (double) i)));
                    exponent = (short) ((int) ((double) exponent % Math.pow(2.0D, (double) i)));
                    ++count2;
                }

                for (i = (short) count2MemStart; i < count3 && count2 != 64; ++i) {
                    data[count2] = (byte) Math.abs(data1[i]);
                    ++count2;
                }
            } else if (pow == 2) {
                count2 = 32;
                if (b > 0.0D) {
                    data[count2] = 0;
                } else {
                    data[count2] = 1;
                    b *= -1.0D;
                }

                ++count2;

                for (Pow = 22; (b != 0.0D || Pow > -2) && count3 != 120; ++count3) {
                    data1[count3] = (byte) ((int) (b / Math.pow(2.0D, (double) Pow)));
                    if (!bool1 && data1[count3] == 1) {
                        count2MemStart = (byte) (count3 + 1);
                        bool1 = true;
                    }

                    if (!bool2 && Pow < 0) {
                        count3MemStart = count3;
                        bool2 = true;
                    }

                    b %= Math.pow(2.0D, (double) Pow);
                    --Pow;
                }

                exponent = (short) ((int) ((double) (count3MemStart - count2MemStart) + Math.pow(2.0D, 7.0D) - 1.0D));

                for (i = 7; i >= 0; --i) {
                    data[count2] = (byte) ((int) ((double) exponent / Math.pow(2.0D, (double) i)));
                    exponent = (short) ((int) ((double) exponent % Math.pow(2.0D, (double) i)));
                    ++count2;
                }

                for (i = (short) count2MemStart; i < count3 && count2 != 64; ++i) {
                    data[count2] = (byte) Math.abs(data1[i]);
                    ++count2;
                }
            }

        }
    }
}