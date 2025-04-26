import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

// Соответствует package org.ama.contest в Kotlin
// Вы можете изменить имя пакета при необходимости
// package org.ama.contest;

public class Main {

    public static void main(String[] args) throws IOException {
        // Используем BufferedReader для эффективного чтения строк
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        // Читаем количество серверов (аналог val numOfServers = readln().trim().toInt())
        // Мы не используем numOfServers в остальной части логики, но читаем строку, как в оригинальном коде
        // Если бы переменная была нужна: final int numOfServers = Integer.parseInt(reader.readLine().trim());
        reader.readLine(); // Прочитать и проигнорировать первую строку (количество серверов)

        // Читаем веса серверов (аналог serverWeights = readln().trim()...)
        final List<Integer> serverWeights = Arrays.stream(reader.readLine().trim().split(" ")) // split(' ') -> split(" ")
                .filter(s -> !s.isEmpty()) // filter { it.isNotEmpty() }
                .map(Integer::parseInt)     // map { it.toInt() }
                .collect(Collectors.toList());// toList()

        // Инициализируем список результатов с начальным значением 0
        // (аналог val res = mutableListOf<Int>(0))
        final List<Integer> res = new ArrayList<>();
        res.add(0);

        // Инициализируем аккумулятор энергии (аналог var accEnergy = 0)
        int accEnergy = 0;

        // Цикл по весам серверов, начиная со второго (индекс 1)
        // (аналог for (i in 1 .. serverWeights.lastIndex))
        // serverWeights.lastIndex в Kotlin это serverWeights.size() - 1 в Java
        for (int i = 1; i < serverWeights.size(); i++) {
            // Текущий вес (аналог val curWeight = serverWeights[i])
            final int curWeight = serverWeights.get(i);

            // Получаем подсписок предыдущих весов и сортируем его
            // (аналог val sortedWeights = serverWeights.subList(0, i).sorted())
            // Важно: subList создает *представление*, а sorted() в Kotlin создает *новый* список.
            // Поэтому мы создаем новый ArrayList из subList и сортируем его.
            List<Integer> sub = new ArrayList<>(serverWeights.subList(0, i));
            Collections.sort(sub); // Сортируем копию подсписка

            // Суммарная энергия для текущего шага (аналог var sumEnergy = 0)
            int sumEnergy = 0;
            // Индекс для обхода отсортированного списка справа налево (аналог var curPos = i - 1)
            int curPos = i - 1; // i - 1 это последний индекс в sublist размером i

            // Ищем элементы в отсортированном списке, которые больше текущего веса
            // (аналог while (curPos >= 0 && curWeight < sortedWeights[curPos]))
            while (curPos >= 0 && curWeight < sub.get(curPos)) {
                // Добавляем вес к сумме энергии (аналог sumEnergy += sortedWeights[curPos])
                sumEnergy += sub.get(curPos);
                // Переходим к следующему элементу слева (аналог curPos--)
                curPos--;
            }

            // Добавляем энергию этого шага к общей накопленной энергии (аналог accEnergy += sumEnergy)
            accEnergy += sumEnergy;
            // Добавляем накопленную энергию в список результатов (аналог res.add(accEnergy))
            res.add(accEnergy);
        }

        // Выводим результат, объединяя элементы списка через пробел
        // (аналог println(res.joinToString(" ")))
        String resultString = res.stream()
                .map(String::valueOf) // Преобразуем каждое число в строку
                .collect(Collectors.joining(" ")); // Объединяем строки через пробел

        System.out.println(resultString);

        // Закрываем ридер (хорошая практика)
        reader.close();
    }
}