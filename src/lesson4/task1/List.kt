@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson4.task1

import lesson1.task1.discriminant
import lesson1.task1.quadraticRootProduct
import lesson1.task1.sqr
import lesson3.task1.digitNumber
import lesson3.task1.isPrime
import java.lang.Math.min
import java.lang.Math.pow
import kotlin.math.sqrt

/**
 * Пример
 *
 * Найти все корни уравнения x^2 = y
 */
fun sqRoots(y: Double) =
        when {
            y < 0 -> listOf()
            y == 0.0 -> listOf(0.0)
            else -> {
                val root = sqrt(y)
                // Результат!
                listOf(-root, root)
            }
        }

/**
 * Пример
 *
 * Найти все корни биквадратного уравнения ax^4 + bx^2 + c = 0.
 * Вернуть список корней (пустой, если корней нет)
 */
fun biRoots(a: Double, b: Double, c: Double): List<Double> {
    if (a == 0.0) {
        return if (b == 0.0) listOf()
        else sqRoots(-c / b)
    }
    val d = discriminant(a, b, c)
    if (d < 0.0) return listOf()
    if (d == 0.0) return sqRoots(-b / (2 * a))
    val y1 = (-b + sqrt(d)) / (2 * a)
    val y2 = (-b - sqrt(d)) / (2 * a)
    return sqRoots(y1) + sqRoots(y2)
}

/**
 * Пример
 *
 * Выделить в список отрицательные элементы из заданного списка
 */
fun negativeList(list: List<Int>): List<Int> {
    val result = mutableListOf<Int>()
    for (element in list) {
        if (element < 0) {
            result.add(element)
        }
    }
    return result
}

/**
 * Пример
 *
 * Изменить знак для всех положительных элементов списка
 */
fun invertPositives(list: MutableList<Int>) {
    for (i in 0 until list.size) {
        val element = list[i]
        if (element > 0) {
            list[i] = -element
        }
    }
}

/**
 * Пример
 *
 * Из имеющегося списка целых чисел, сформировать список их квадратов
 */
fun squares(list: List<Int>) = list.map { it * it }

/**
 * Пример
 *
 * Из имеющихся целых чисел, заданного через vararg-параметр, сформировать массив их квадратов
 */
fun squares(vararg array: Int) = squares(array.toList()).toTypedArray()

/**
 * Пример
 *
 * По заданной строке str определить, является ли она палиндромом.
 * В палиндроме первый символ должен быть равен последнему, второй предпоследнему и т.д.
 * Одни и те же буквы в разном регистре следует считать равными с точки зрения данной задачи.
 * Пробелы не следует принимать во внимание при сравнении символов, например, строка
 * "А роза упала на лапу Азора" является палиндромом.
 */
fun isPalindrome(str: String): Boolean {
    val lowerCase = str.toLowerCase().filter { it != ' ' }
    for (i in 0..lowerCase.length / 2) {
        if (lowerCase[i] != lowerCase[lowerCase.length - i - 1]) return false
    }
    return true
}

/**
 * Пример
 *
 * По имеющемуся списку целых чисел, например [3, 6, 5, 4, 9], построить строку с примером их суммирования:
 * 3 + 6 + 5 + 4 + 9 = 27 в данном случае.
 */
fun buildSumExample(list: List<Int>) = list.joinToString(separator = " + ", postfix = " = ${list.sum()}")

/**
 * Простая
 *
 * Найти модуль заданного вектора, представленного в виде списка v,
 * по формуле abs = sqrt(a1^2 + a2^2 + ... + aN^2).
 * Модуль пустого вектора считать равным 0.0.
 */
fun abs(v: List<Double>): Double {
    var sq = 0.0
    if (v.isNotEmpty()) {
        for (element in v) sq += sqr(element)
    }
    return sqrt(sq)
}

/**
 * Простая
 *
 * Рассчитать среднее арифметическое элементов списка list. Вернуть 0.0, если список пуст
 */
fun mean(list: List<Double>): Double {
    var midNum = 0.0
    if (list.isNotEmpty()) midNum = list.sum() / list.size
    return midNum
}

/**
 * Средняя
 *
 * Центрировать заданный список list, уменьшив каждый элемент на среднее арифметическое всех элементов.
 * Если список пуст, не делать ничего. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun center(list: MutableList<Double>): MutableList<Double> {
    if (list.isNotEmpty()) {
        var mid = mean(list)
        for (i in 0 until list.size) {
            list[i] -= mid
        }
    }
    return list
}

/**
 * Средняя
 *
 * Найти скалярное произведение двух векторов равной размерности,
 * представленные в виде списков a и b. Скалярное произведение считать по формуле:
 * C = a1b1 + a2b2 + ... + aNbN. Произведение пустых векторов считать равным 0.0.
 */
fun times(a: List<Double>, b: List<Double>): Double {
    var sum = 0.0
    if (a.isNotEmpty()) {
        for (i in 0 until a.size) {
            sum += a[i] * b[i]
        }
    }
    return sum
}

/**
 * Средняя
 *
 * Рассчитать значение многочлена при заданном x:
 * p(x) = p0 + p1*x + p2*x^2 + p3*x^3 + ... + pN*x^N.
 * Коэффициенты многочлена заданы списком p: (p0, p1, p2, p3, ..., pN).
 * Значение пустого многочлена равно 0.0 при любом x.
 */
fun polynom(p: List<Double>, x: Double): Double {
    var sum = 0.0
    var i = 0
    if (p.isNotEmpty()) {
        for (element in p) {
            sum += element * pow(x, i.toDouble())
            i++
        }
    }
    return sum
}

/**
 * Средняя
 *
 * В заданном списке list каждый элемент, кроме первого, заменить
 * суммой данного элемента и всех предыдущих.
 * Например: 1, 2, 3, 4 -> 1, 3, 6, 10.
 * Пустой список не следует изменять. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun accumulate(list: MutableList<Double>): MutableList<Double> {
    if (list.isNotEmpty()) {
        var prv = list[0]
        var tm: Double
        for (i in 1 until list.size) {
            tm = prv
            prv += list[i]
            list[i] += tm
        }
    }
    return list
}

/**
 * Средняя
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде списка множителей, например 75 -> (3, 5, 5).
 * Множители в списке должны располагаться по возрастанию.
 */
fun factorize(n: Int): List<Int> {
    var num = n
    var sep = 2
    val result = mutableListOf<Int>()
    while (!isPrime(num)) {
        if (num % sep == 0 && isPrime(sep)) {
            num /= sep
            result.add(sep)
        }
        else sep++
    }
    result.add(num)
    return result
}

/**
 * Сложная
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде строки, например 75 -> 3*5*5
 * Множители в результирующей строке должны располагаться по возрастанию.
 */
fun factorizeToString(n: Int): String = factorize(n).joinToString(separator = "*")


/**
 * Средняя
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием base > 1.
 * Результат перевода вернуть в виде списка цифр в base-ичной системе от старшей к младшей,
 * например: n = 100, base = 4 -> (1, 2, 1, 0) или n = 250, base = 14 -> (1, 3, 12)
 */
fun convert(n: Int, base: Int): List<Int> {
    var res = mutableListOf<Int>()
    if (n == 1 || n < base) return listOf(n)
    var num = n
    while (num > 0) {
        res.add(num % base)
        num /= base
    }
    return res.reversed()
}

/**
 * Сложная
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием 1 < base < 37.
 * Результат перевода вернуть в виде строки, цифры более 9 представлять латинскими
 * строчными буквами: 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: n = 100, base = 4 -> 1210, n = 250, base = 14 -> 13c
 */
fun convertToString(n: Int, base: Int): String {
    var list = convert(n, base)
    var res = mutableListOf<String>()
    for (i in 0 until list.size) {
        if (list[i] >= 10) res.add((87 + list[i]).toChar().toString())
        else res.add(list[i].toString())
    }
    return res.joinToString(separator = "")
}

/**
 * Средняя
 *
 * Перевести число, представленное списком цифр digits от старшей к младшей,
 * из системы счисления с основанием base в десятичную.
 * Например: digits = (1, 3, 12), base = 14 -> 250
 */
fun decimal(digits: List<Int>, base: Int): Int {
    var num = 0.0
    for (i in 0 until digits.size) num += pow(base.toDouble(), (digits.size - 1 - i).toDouble()) * digits[i]
    return num.toInt()
}

/**
 * Сложная
 *
 * Перевести число, представленное цифровой строкой str,
 * из системы счисления с основанием base в десятичную.
 * Цифры более 9 представляются латинскими строчными буквами:
 * 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: str = "13c", base = 14 -> 250
 */
fun decimalFromString(str: String, base: Int): Int {
    var list = mutableListOf<Int>()
    for (char in str) {
        if (char in '0'..'9') list.add(char.toString().toInt())
        else list.add((char).toInt() - 87)
    }
    return decimal(list, base)
}

/**
 * Сложная
 *
 * Перевести натуральное число n > 0 в римскую систему.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: 23 = XXIII, 44 = XLIV, 100 = C
 */
fun roman(n: Int): String {
    var l = listOf("I", "V", "X", "L", "C", "D", "M")
    var num = mutableListOf<String>()
    var x = n
    for (i in 0..6 step 2) {
        if (x == 0) break else {
            var len = x % 10
            if (len != 0) when {
                len in 1..3 -> for (j in 1..len) num.add(l[i])
                len == 4 -> {
                    num.add(l[i + 1])
                    num.add(l[i])
                }
                len in 5..8 -> {
                    for (j in 5 until len) num.add(l[i])
                    num.add(l[i + 1])
                }
                else -> {
                    num.add(l[i + 2])
                    num.add(l[i])
                }
            }
            x /= 10
        }
    }
    return num.reversed().joinToString(separator = "")
}

/**
 * Очень сложная
 *
 * Записать заданное натуральное число 1..999999 прописью по-русски.
 * Например, 375 = "триста семьдесят пять",
 * 23964 = "двадцать три тысячи девятьсот шестьдесят четыре"
 */
fun russian(n: Int): String {
    val l = arrayOf(
            arrayOf("", "один ", "два ", "три ", "четыре ", "пять ", "шесть ", "семь ", "восемь ", "девять "),
            arrayOf("", "", "двадцать ", "тридцать ", "сорок ", "пятьдесят ", "шестьдесят ", "семьдесят ",
                    "восемьдесят ", "девяносто "),
            arrayOf("", "сто ", "двести ", "триста ", "четыреста ", "пятьсот ", "шестьсот ", "семьсот ", "восемьсот ",
                    "девятьсот ")
    )
    val thn = arrayOf("десять ", "одиннадцать ", "двенадцать ", "тринадцать ", "четырнадцать ", "пятнадцать ",
            "шестнадцать ", "семнадцать ", "восемнадцать ", "девятнадцать ")
    val end = arrayOf(
            arrayOf("", " ", " ", " "),
            arrayOf("тысяч", "а ", "и ", " "),
            arrayOf("миллион", " ", "а ", "ов "),
            arrayOf("миллиард", " ", "а ", "ов ")
    )
    val ix = arrayOf("", "одна ", "две ", "три ", "четыре ", "пять ", "шесть ", "семь ", "восемь ", "девять ")
    var num = mutableListOf<Int>()
    var str = ""
    var x = n
    while (x != 0) {
        num.add(x % 1000)
        x /= 1000
    }
    num = num.asReversed()
    for (i in 0 until num.size) {
        var nxt = if (num[i] % 100 in 10..19) true else false
        var ndng = ""
        var len = digitNumber(num[i])
        for (j in 0 until len) {
            var sas = num[i] / pow(10.0, (len - 1 - j).toDouble()).toInt()
            str += when {
                j == len - 1 -> when {
                    nxt -> ""
                    i == num.size - 2 -> ix[sas]
                    else -> l[0][sas]
                }
                j == len - 2 -> if (nxt) thn[num[i] % 10] else l[1][sas]
                else -> l[2][sas]
            }
            num[i] %= pow(10.0, (len - 1 - j).toDouble()).toInt()
            if (num[i] == 0) {
                var a = when {
                    sas in 5..9 || nxt -> 3
                    sas in 2..4 -> 2
                    sas == 1 -> 1
                    else -> 3
                }
                ndng = end[num.size - i - 1][a]
            }
        }
        str += end[num.size - i - 1][0] + ndng
    }
    return str.trim()
}
