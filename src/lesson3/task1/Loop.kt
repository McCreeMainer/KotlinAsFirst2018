@file:Suppress("UNUSED_PARAMETER")
package lesson3.task1

import lesson1.task1.sqr
import java.lang.Math.pow
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sqrt

/**
 * Пример
 *
 * Вычисление факториала
 */
fun factorial(n: Int): Double {
    var result = 1.0
    for (i in 1..n) {
        result = result * i // Please do not fix in master
    }
    return result
}

/**
 * Пример
 *
 * Проверка числа на простоту -- результат true, если число простое
 */
fun isPrime(n: Int): Boolean {
    if (n < 2) return false
    if (n == 2) return true
    if (n % 2 == 0) return false
    for (m in 3..sqrt(n.toDouble()).toInt() step 2) {
        if (n % m == 0) return false
    }
    return true
}

/**
 * Пример
 *
 * Проверка числа на совершенность -- результат true, если число совершенное
 */
fun isPerfect(n: Int): Boolean {
    var sum = 1
    for (m in 2..n/2) {
        if (n % m > 0) continue
        sum += m
        if (sum > n) break
    }
    return sum == n
}

/**
 * Пример
 *
 * Найти число вхождений цифры m в число n
 */
fun digitCountInNumber(n: Int, m: Int): Int =
        when {
            n == m -> 1
            n < 10 -> 0
            else -> digitCountInNumber(n / 10, m) + digitCountInNumber(n % 10, m)
        }

/**
 * Тривиальная
 *
 * Найти количество цифр в заданном числе n.
 * Например, число 1 содержит 1 цифру, 456 -- 3 цифры, 65536 -- 5 цифр.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun digitNumber(n: Int): Int {
    var a = 0
    var b = n
    do {
        b /= 10
        a++
    } while (b > 0)
    return a
}

/**
 * Простая
 *
 * Найти число Фибоначчи из ряда 1, 1, 2, 3, 5, 8, 13, 21, ... с номером n.
 * Ряд Фибоначчи определён следующим образом: fib(1) = 1, fib(2) = 1, fib(n+2) = fib(n) + fib(n+1)
 */
fun fib(n: Int): Int {
    var a = 0
    var b = 0
    var c: Int
    for (i in 1..n) {
        if (i == 1) {
            b = 1
        }
        else {
            c = a
            a = b
            b = b + c

        }
    }
    return b
}

/**
 * Простая
 *
 * Для заданных чисел m и n найти наименьшее общее кратное, то есть,
 * минимальное число k, которое делится и на m и на n без остатка
 */
fun lcm(m: Int, n: Int): Int {
    var a = max(m, n)
    var b = min(m, n)
    var c = 1
    while (a * c % b != 0) {
        c++
    }
    return a * c
}

/**
 * Простая
 *
 * Для заданного числа n > 1 найти минимальный делитель, превышающий 1
 */
fun minDivisor(n: Int): Int {
    var a = n
    if (n % 2 == 0) a = 2
    else {
        for (i in 3..(n / 3) step 2) {
            if (n % i == 0) {
                a = i
                break
            }
        }
    }
    return a
}

/**ч
 * Простая
 *
 * Для заданного числа n > 1 найти максимальный делитель, меньший n
 */
fun maxDivisor(n: Int): Int {
    var a = n / 3
    if (n % 2 == 0) a = n / 2
    else {
        if (n / 3 % 2 == 0) a += 1
        else a += 2
        while (n % a != 0) a -= 2
    }
    return a
}

/**
 * Простая
 *
 * Определить, являются ли два заданных числа m и n взаимно простыми.
 * Взаимно простые числа не имеют общих делителей, кроме 1.
 * Например, 25 и 49 взаимно простые, а 6 и 8 -- нет.
 */
fun isCoPrime(m: Int, n: Int): Boolean {
    var a = true
    if (m % 2 == 0 && n % 2 == 0) {
        a = false
    }
    else for (i in 3..min(m, n) step 2) {
        if (m % i == 0 && n % i == 0) {
            a = false
            break
        }
    }
    return a
}

/**
 * Простая
 *
 * Для заданных чисел m и n, m <= n, определить, имеется ли хотя бы один точный квадрат между m и n,
 * то есть, существует ли такое целое k, что m <= k*k <= n.
 * Например, для интервала 21..28 21 <= 5*5 <= 28, а для интервала 51..61 квадрата не существует.
 */
fun squareBetweenExists(m: Int, n: Int): Boolean {
    var a = false
    for (i in sqrt(m.toDouble()).toInt()..sqrt(n.toDouble()).toInt()) {
        if (sqr(i) >= m && sqr(i) <= n) {
            a = true
            break
        }
    }
    return a
}

/**
 * Средняя
 *
 * Гипотеза Коллатца. Рекуррентная последовательность чисел задана следующим образом:
 *
 *   ЕСЛИ (X четное)
 *     Xслед = X /2
 *   ИНАЧЕ
 *     Xслед = 3 * X + 1
 *
 * например
 *   15 46 23 70 35 106 53 160 80 40 20 10 5 16 8 4 2 1 4 2 1 4 2 1 ...
 * Данная последовательность рано или поздно встречает X == 1.
 * Написать функцию, которая находит, сколько шагов требуется для
 * этого для какого-либо начального X > 0.
 */
fun collatzSteps(x: Int): Int {
    var a = 0
    var b = x
    while (b > 1) {
        if (b % 2 == 0) {
            b /= 2
            a++
        }
        else {
            b = b * 3 + 1
            a++
        }
    }
    return a
}

/**
 * Средняя
 *
 * Для заданного x рассчитать с заданной точностью eps
 * sin(x) = x - x^3 / 3! + x^5 / 5! - x^7 / 7! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю
 */
fun sin(x: Double, eps: Double): Double {
    var unit = x % (Math.PI * 2)
    var fac = 1
    var wop = 1.0
    var befr = 1
    var sum = 0.0
    while (abs(pow(unit, wop) / fac) >= abs(eps)) {
        sum += befr * pow(unit, wop) / fac
        wop += 2
        fac *= (fac + 1) * (fac + 2)
        befr *= -1
    }
    return sum + befr * pow(unit, wop) / fac
}
/**
 * Средняя
 *
 * Для заданного x рассчитать с заданной точностью eps
 * cos(x) = 1 - x^2 / 2! + x^4 / 4! - x^6 / 6! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю
 */
fun cos(x: Double, eps: Double): Double = TODO()

/**
 * Средняя
 *
 * Поменять порядок цифр заданного числа n на обратный: 13478 -> 87431.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun revert(n: Int): Int {
    var len = 1
    var num = 0.0
    var x = 10
    var bfr = 0.0
    while (n % x != n) {
        len++
        x *= 10
    }
    if (len == 1) num = n.toDouble()
    else {
        for (i in 1..len) {
            num += (n % pow(10.0, i * 1.0) - bfr) / pow(10.0, (i - 1) * 1.0) * pow(10.0, (len - i).toDouble())
            bfr = n % pow(10.0, i * 1.0)
        }
    }
    return num.toInt()
}

/**
 * Средняя
 *
 * Проверить, является ли заданное число n палиндромом:
 * первая цифра равна последней, вторая -- предпоследней и так далее.
 * 15751 -- палиндром, 3653 -- нет.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun isPalindrome(n: Int): Boolean {
    var len = 1
    var x = 10
    var k = 1
    var res = true
    var bfr = 0
    while (n % x != n) {
        len++
        x *= 10
    }
    if (len % 2 == 0) k = 0
    for (i in 1..len) {
        if ((n % pow(10.0, i * 1.0) - bfr) / pow(10.0, (i - 1) * 1.0)) {
            pow(10.0, (i - 1) * 1.0) * pow(10.0, (len - i).toDouble())
            res = false
            break
        }
    }
    return res
}
/**
 * Средняя
 *
 * Для заданного числа n определить, содержит ли оно различающиеся цифры.
 * Например, 54 и 323 состоят из разных цифр, а 111 и 0 из одинаковых.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun hasDifferentDigits(n: Int): Boolean = TODO()

/**
 * Сложная
 *
 * Найти n-ю цифру последовательности из квадратов целых чисел:
 * 149162536496481100121144...
 * Например, 2-я цифра равна 4, 7-я 5, 12-я 6.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun squareSequenceDigit(n: Int): Int = TODO()

/**
 * Сложная
 *
 * Найти n-ю цифру последовательности из чисел Фибоначчи (см. функцию fib выше):
 * 1123581321345589144...
 * Например, 2-я цифра равна 1, 9-я 2, 14-я 5.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun fibSequenceDigit(n: Int): Int = TODO()
