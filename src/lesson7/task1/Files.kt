@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence", "IMPLICIT_CAST_TO_ANY")

package lesson7.task1
import lesson3.task1.digitNumber
import java.io.BufferedWriter
import java.io.File

/**
 * Пример
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * Вывести его в выходной файл с именем outputName, выровняв по левому краю,
 * чтобы длина каждой строки не превосходила lineLength.
 * Слова в слишком длинных строках следует переносить на следующую строку.
 * Слишком короткие строки следует дополнять словами из следующей строки.
 * Пустые строки во входном файле обозначают конец абзаца,
 * их следует сохранить и в выходном файле
 */
fun alignFile(inputName: String, lineLength: Int, outputName: String) {
    val outputStream = File(outputName).bufferedWriter()
    var currentLineLength = 0
    for (line in File(inputName).readLines()) {
        if (line.isEmpty()) {
            outputStream.newLine()
            if (currentLineLength > 0) {
                outputStream.newLine()
                currentLineLength = 0
            }
            continue
        }
        for (word in line.split(" ")) {
            if (currentLineLength > 0) {
                if (word.length + currentLineLength >= lineLength) {
                    outputStream.newLine()
                    currentLineLength = 0
                } else {
                    outputStream.write(" ")
                    currentLineLength++
                }
            }
            outputStream.write(word)
            currentLineLength += word.length
        }
    }
    outputStream.close()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * На вход подаётся список строк substrings.
 * Вернуть ассоциативный массив с числом вхождений каждой из строк в текст.
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 *
 */
fun countSubstrings(inputName: String, substrings: List<String>): Map<String, Int> {
    val reader = File(inputName).bufferedReader().readLines().joinToString("\n")
    val mp = mutableMapOf<String, Int>()
    substrings.forEach {
        mp[it] = 0
        var t = Regex(it, RegexOption.IGNORE_CASE).find(reader)
        while (t != null) {
            mp[it] = (mp[it] ?: 0) + 1
            t = Regex(it, RegexOption.IGNORE_CASE).find(reader, t.range.first + 1)
        }
    }
    return mp
}

/**
 * Средняя
 *
 * В русском языке, как правило, после букв Ж, Ч, Ш, Щ пишется И, А, У, а не Ы, Я, Ю.
 * Во входном файле с именем inputName содержится некоторый текст на русском языке.
 * Проверить текст во входном файле на соблюдение данного правила и вывести в выходной
 * файл outputName текст с исправленными ошибками.
 *
 * Регистр заменённых букв следует сохранять.
 *
 * Исключения (жюри, брошюра, парашют) в рамках данного задания обрабатывать не нужно
 *
 */
fun sibilants(inputName: String, outputName: String) {
    val wrtr = File(outputName).bufferedWriter()
    File(inputName).bufferedReader().readLines().forEach {
        var prv = ""
        for (c in it) {
            val str =
                    if (Regex("""[жчшщ][ыяю]""", RegexOption.IGNORE_CASE).matches(prv + c))
                        when (c.toLowerCase()) {
                            'ы' -> c - 19
                            'я' -> c - 31
                            'ю' -> c - 11
                            else -> throw IllegalArgumentException()
                        }
                    else c
            wrtr.write(str.toString())
            prv = c.toString()
        }
        wrtr.newLine()
    }
    wrtr.close()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 * Вывести его в выходной файл с именем outputName, выровняв по центру
 * относительно самой длинной строки.
 *
 * Выравнивание следует производить путём добавления пробелов в начало строки.
 *
 *
 * Следующие правила должны быть выполнены:
 * 1) Пробелы в начале и в конце всех строк не следует сохранять.
 * 2) В случае невозможности выравнивания строго по центру, строка должна быть сдвинута в ЛЕВУЮ сторону
 * 3) Пустые строки не являются особым случаем, их тоже следует выравнивать
 * 4) Число строк в выходном файле должно быть равно числу строк во входном (в т. ч. пустых)
 *
 */
fun centerFile(inputName: String, outputName: String) {
    val reader = File(inputName).bufferedReader().readLines()
    val wrtr = File(outputName).bufferedWriter()
    val mx = reader.map { it.trim().length }.max() ?: 0
    reader.forEach {
        var str = " ".repeat((mx - it.trim().length) / 2) + it.trim()
        wrtr.write(str)
        wrtr.newLine()
    }
    wrtr.close()
}

/**
 * Сложная
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 * Вывести его в выходной файл с именем outputName, выровняв по левому и правому краю относительно
 * самой длинной строки.
 * Выравнивание производить, вставляя дополнительные пробелы между словами: равномерно по всей строке
 *
 * Слова внутри строки отделяются друг от друга одним или более пробелом.
 *
 * Следующие правила должны быть выполнены:
 * 1) Каждая строка входного и выходного файла не должна начинаться или заканчиваться пробелом.
 * 2) Пустые строки или строки из пробелов трансформируются в пустые строки без пробелов.
 * 3) Строки из одного слова выводятся без пробелов.
 * 4) Число строк в выходном файле должно быть равно числу строк во входном (в т. ч. пустых).
 *
 * Равномерность определяется следующими формальными правилами:
 * 5) Число пробелов между каждыми двумя парами соседних слов не должно отличаться более, чем на 1.
 * 6) Число пробелов между более левой парой соседних слов должно быть больше или равно числу пробелов
 *    между более правой парой соседних слов.
 *
 * Следует учесть, что входной файл может содержать последовательности из нескольких пробелов  между слвоами. Такие
 * последовательности следует учитывать при выравнивании и при необходимости избавляться от лишних пробелов.
 * Из этого следуют следующие правила:
 * 7) В самой длинной строке каждая пара соседних слов должна быть отделена В ТОЧНОСТИ одним пробелом
 * 8) Если входной файл удовлетворяет требованиям 1-7, то он должен быть в точности идентичен выходному файлу
 */
fun alignFileByWidth(inputName: String, outputName: String) {
    val reader = File(inputName).bufferedReader().readLines().map { it.trim().replace(Regex("""\s+"""), " ") }
    val wrtr = File(outputName).bufferedWriter()
    val mx = reader.map { it.length }.max() ?: 0
    reader.forEach {
        if (it.split(" ").size > 1 && it.length != mx) {
            val spcs = mx - it.length
            val spc = it.split(" ").size - 1
            var b = spcs % spc
            var ll = " "
            for (i in 0 until spcs / spc) ll += " "
            for (i in 0 until it.length) {
                val str = if (it[i] == ' ') ll + if (b > 0) {
                    b--
                    " "
                } else ""
                else it[i]
                wrtr.write(str.toString())
            }
        } else wrtr.write(it)
        wrtr.newLine()
    }
    wrtr.close()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 *
 * Вернуть ассоциативный массив, содержащий 20 наиболее часто встречающихся слов с их количеством.
 * Если в тексте менее 20 различных слов, вернуть все слова.
 *
 * Словом считается непрерывная последовательность из букв (кириллических,
 * либо латинских, без знаков препинания и цифр).
 * Цифры, пробелы, знаки препинания считаются разделителями слов:
 * Привет, привет42, привет!!! -привет?!
 * ^ В этой строчке слово привет встречается 4 раза.
 *
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 * Ключи в ассоциативном массиве должны быть в нижнем регистре.
 *
 */
fun top20Words(inputName: String): Map<String, Int> {
    val reader = File(inputName).bufferedReader().readLines()
    val mp = mutableMapOf<String, Int>()
    if (reader.isNotEmpty())
        reader.joinToString(" ").toLowerCase().split(Regex("""[^a-zа-яё]+"""))
                .forEach { mp[it] = (mp[it] ?: 0) + 1 }
    return mp.toList().filter { it.first != "" }.sortedByDescending { it.second }.take(20).toMap()
}

/**
 * Средняя
 *
 * Реализовать транслитерацию текста из входного файла в выходной файл посредством динамически задаваемых правил.

 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 *
 * В ассоциативном массиве dictionary содержится словарь, в котором некоторым символам
 * ставится в соответствие строчка из символов, например
 * mapOf('з' to "zz", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "yy", '!' to "!!!")
 *
 * Необходимо вывести в итоговый файл с именем outputName
 * содержимое текста с заменой всех символов из словаря на соответствующие им строки.
 *
 * При этом регистр символов в словаре должен игнорироваться,
 * но при выводе символ в верхнем регистре отображается в строку, начинающуюся с символа в верхнем регистре.
 *
 * Пример.
 * Входной текст: Здравствуй, мир!
 *
 * заменяется на
 *
 * Выходной текст: Zzdrавствуy, mир!!!
 *
 * Пример 2.
 *
 * Входной текст: Здравствуй, мир!
 * Словарь: mapOf('з' to "zZ", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "YY", '!' to "!!!")
 *
 * заменяется на
 *
 * Выходной текст: Zzdrавствуy, mир!!!
 *
 * Обратите внимание: данная функция не имеет возвращаемого значения
 */
fun transliterate(inputName: String, dictionary: Map<Char, String>, outputName: String) {
    val reader = File(inputName).bufferedReader().readLines()
    val wrtr = File(outputName).bufferedWriter()
    reader.forEach {
        for (i in 0 until it.length) {
            val str = when {
                dictionary[it[i]] != null || dictionary[it[i].toUpperCase()] != null ->
                    dictionary[it[i]]?.toLowerCase()
                            ?: dictionary[it[i].toUpperCase()]?.toLowerCase() ?: ""
                dictionary[it[i].toLowerCase()] != null ->
                    dictionary[it[i].toLowerCase()]?.toLowerCase()?.capitalize() ?: ""
                else -> it[i].toString()
            }
            wrtr.write(str)
        }
        wrtr.newLine()
    }
    wrtr.close()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName имеется словарь с одним словом в каждой строчке.
 * Выбрать из данного словаря наиболее длинное слово,
 * в котором все буквы разные, например: Неряшливость, Четырёхдюймовка.
 * Вывести его в выходной файл с именем outputName.
 * Если во входном файле имеется несколько слов с одинаковой длиной, в которых все буквы разные,
 * в выходной файл следует вывести их все через запятую.
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 *
 * Пример входного файла:
 * Карминовый
 * Боязливый
 * Некрасивый
 * Остроумный
 * БелогЛазый
 * ФиолетОвый

 * Соответствующий выходной файл:
 * Карминовый, Некрасивый
 *
 * Обратите внимание: данная функция не имеет возвращаемого значения
 */
fun chooseLongestChaoticWord(inputName: String, outputName: String) {
    val wrtr = File(outputName).bufferedWriter()
    val reader = File(inputName).bufferedReader().readLines()
    val mx = reader.filter { it.toLowerCase().toSet().size == it.length }.maxBy { it.length }?.length ?: 0
    val str = mutableListOf<String>()
    reader.forEach { if (it.length == mx) str.add(it) }
    wrtr.write(reader.filter { it.toLowerCase().toSet().size == it.length && it.length == mx }.joinToString(", "))
    wrtr.close()
}

/**
 * Сложная
 *
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 *
 * Во входном файле с именем inputName содержится текст, содержащий в себе элементы текстовой разметки следующих типов:
 * - *текст в курсивном начертании* -- курсив
 * - **текст в полужирном начертании** -- полужирный
 * - ~~зачёркнутый текст~~ -- зачёркивание
 *
 * Следует вывести в выходной файл этот же текст в формате HTML:
 * - <i>текст в курсивном начертании</i>
 * - <b>текст в полужирном начертании</b>
 * - <s>зачёркнутый текст</s>
 *
 * Кроме того, все абзацы исходного текста, отделённые друг от друга пустыми строками, следует обернуть в теги <p>...</p>,
 * а весь текст целиком в теги <html><body>...</body></html>.
 *
 * Все остальные части исходного текста должны остаться неизменными с точностью до наборов пробелов и переносов строк.
 * Отдельно следует заметить, что открывающая последовательность из трёх звёздочек (***) должна трактоваться как "<b><i>"
 * и никак иначе.
 *
 * Пример входного файла:
Lorem ipsum *dolor sit amet*, consectetur **adipiscing** elit.
Vestibulum lobortis, ~~Est vehicula rutrum *suscipit*~~, ipsum ~~lib~~ero *placerat **tortor***,

Suspendisse ~~et elit in enim tempus iaculis~~.
 *
 * Соответствующий выходной файл:
<html>
<body>
<p>
Lorem ipsum <i>dolor sit amet</i>, consectetur <b>adipiscing</b> elit.
Vestibulum lobortis. <s>Est vehicula rutrum <i>suscipit</i></s>, ipsum <s>lib</s>ero <i>placerat <b>tortor</b></i>.
</p>
<p>
Suspendisse <s>et elit in enim tempus iaculis</s>.
</p>
</body>
</html>
 *
 * (Отступы и переносы строк в примере добавлены для наглядности, при решении задачи их реализовывать не обязательно)
 */
fun markdownToHtmlSimple(inputName: String, outputName: String) {
    val wrtr = File(outputName).bufferedWriter()
    val reader = File(inputName).bufferedReader().readLines()
    var i = false
    var b = false
    var s = false
    var nxt = 0
    wrtr.write("<html><body><p>")
    reader.forEach {
        if (it.isEmpty()) wrtr.write("</p><p>")
        else {
            for (str in 0 until it.length) {
                if (nxt > 0) {
                    nxt--
                    continue
                } else when {
                    it[str] == '*' && it[str + 1] == '*' -> {
                        if (!b && it.indexOf("**", str + 2) != -1) wrtr.write("<b>") else wrtr.write("</b>")
                        nxt = 1
                        b = !b
                    }
                    it[str] == '~' && it[str + 1] == '~' -> {
                        if (!s && it.indexOf("~~", str + 2) != -1) wrtr.write("<s>") else wrtr.write("</s>")
                        nxt = 1
                        s = !s
                    }
                    it[str] == '*' -> {
                        if (!i && it.indexOf("*", str + 1) != -1) wrtr.write("<i>") else wrtr.write("</i>")
                        i = !i
                    }
                    else -> wrtr.write(it[str].toString())
                }
            }
        }
    }
    wrtr.write("</p></body></html>")
    wrtr.close()
}

/**
 * Сложная
 *
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 *
 * Во входном файле с именем inputName содержится текст, содержащий в себе набор вложенных друг в друга списков.
 * Списки бывают двух типов: нумерованные и ненумерованные.
 *
 * Каждый элемент ненумерованного списка начинается с новой строки и символа '*', каждый элемент нумерованного списка --
 * с новой строки, числа и точки. Каждый элемент вложенного списка начинается с отступа из пробелов, на 4 пробела большего,
 * чем список-родитель. Максимально глубина вложенности списков может достигать 6. "Верхние" списки файла начинются
 * прямо с начала строки.
 *
 * Следует вывести этот же текст в выходной файл в формате HTML:
 * Нумерованный список:
 * <ol>
 *     <li>Раз</li>
 *     <li>Два</li>
 *     <li>Три</li>
 * </ol>
 *
 * Ненумерованный список:
 * <ul>
 *     <li>Раз</li>
 *     <li>Два</li>
 *     <li>Три</li>
 * </ul>
 *
 * Кроме того, весь текст целиком следует обернуть в теги <html><body>...</body></html>
 *
 * Все остальные части исходного текста должны остаться неизменными с точностью до наборов пробелов и переносов строк.
 *
 * Пример входного файла:
///////////////////////////////начало файла/////////////////////////////////////////////////////////////////////////////
 * Утка по-пекински
 * Утка
 * Соус
 * Салат Оливье
1. Мясо
 * Или колбаса
2. Майонез
3. Картофель
4. Что-то там ещё
 * Помидоры
 * Фрукты
1. Бананы
23. Яблоки
1. Красные
2. Зелёные
///////////////////////////////конец файла//////////////////////////////////////////////////////////////////////////////
 *
 *
 * Соответствующий выходной файл:
///////////////////////////////начало файла/////////////////////////////////////////////////////////////////////////////
<html>
<body>
<ul>
<li>
Утка по-пекински
<ul>
<li>Утка</li>
<li>Соус</li>
</ul>
</li>
<li>
Салат Оливье
<ol>
<li>Мясо
<ul>
<li>
Или колбаса
</li>
</ul>
</li>
<li>Майонез</li>
<li>Картофель</li>
<li>Что-то там ещё</li>
</ol>
</li>
<li>Помидоры</li>
<li>
Яблоки
<ol>
<li>Красные</li>
<li>Зелёные</li>
</ol>
</li>
</ul>
</body>
</html>
///////////////////////////////конец файла//////////////////////////////////////////////////////////////////////////////
 * (Отступы и переносы строк в примере добавлены для наглядности, при решении задачи их реализовывать не обязательно)
 */
fun markdownToHtmlLists(inputName: String, outputName: String) {
    val rdr = File(inputName).bufferedReader().readLines()
    val wrtr = File(outputName).bufferedWriter()
    wrtr.write("<html><body>")
    htmlList(rdr, wrtr, false)
    wrtr.write("</body></html>")
    wrtr.close()
}

/**
 * Очень сложная
 *
 * Реализовать преобразования из двух предыдущих задач одновременно над одним и тем же файлом.
 * Следует помнить, что:
 * - Списки, отделённые друг от друга пустой строкой, являются разными и должны оказаться в разных параграфах выходного файла.
 *
 */
fun markdownToHtml(inputName: String, outputName: String) {
    val rdr = File(inputName).bufferedReader().readLines()
    val wrtr = File(outputName).bufferedWriter()
    wrtr.write("<html><body>")
    htmlList(rdr, wrtr, true)
    wrtr.write("</body></html>")
    wrtr.close()
}

fun htmlList(rdr: List<String>, wrtr: BufferedWriter, lst: Boolean) {
    var i = false
    var b = false
    var s = false
    var nxt = 0
    var sas = -1
    val list = mutableListOf<String>()
    rdr.forEach {
        var type = ""
        var res = it
        if (Regex("""^\s*\*.*$""").matches(it)) {
            res = res.replace(Regex("""^\s*\*\s*"""), "")
            type = "ul"
        } else {
            res = res.replace(Regex("""^\s*\d+\.\s*"""), "")
            type = "ol"
        }
        var txt = res
        if (lst) {
            txt = ""
            if (res.isEmpty()) txt = "</p><p>"
            else {
                for (str in 0 until res.length) {
                    if (nxt > 0) {
                        nxt--
                        continue
                    } else when {
                        it[str] == '*' && it[str + 1] == '*' -> {
                            txt += if (!b && it.indexOf("**", str + 2) != -1) "<b>" else "</b>"
                            nxt = 1
                            b = !b
                        }
                        it[str] == '~' && it[str + 1] == '~' -> {
                            txt += if (!s && it.indexOf("~~", str + 2) != -1) "<s>" else "</s>"
                            nxt = 1
                            s = !s
                        }
                        it[str] == '*' -> {
                            txt += if (!i && it.indexOf("*", str + 1) != -1) "<i>" else "</i>"
                            i = !i
                        }
                        else -> txt += res[str].toString()
                    }
                }
            }
        }
        val sos = Regex("""(?:\*|\d+\.)""").find(it)!!.range.first
        if (sos > sas) {
            list.add(type)
            wrtr.write("<$type><li>$res")
            sas = sos
        } else {
            if (sos < sas) {
                for (i in sos + 4..sas step 4) {
                    val cls = list.last()
                    wrtr.write("</li></$cls>")
                    list.removeAt(list.lastIndex)
                }
                sas = sos
            }
            wrtr.write("</li><li>$txt")
        }
    }
    while (list.isNotEmpty()) {
        val cls = list.last()
        wrtr.write("</li></$cls>")
        list.removeAt(list.lastIndex)
    }
}

/**
 * Средняя
 *
 * Вывести в выходной файл процесс умножения столбиком числа lhv (> 0) на число rhv (> 0).
 *
 * Пример (для lhv == 19935, rhv == 111):
19935
 *    111
--------
19935
+ 19935
+19935
--------
2212785
 * Используемые пробелы, отступы и дефисы должны в точности соответствовать примеру.
 * Нули в множителе обрабатывать так же, как и остальные цифры:
235
 *  10
-----
0
+235
-----
2350
 *
 */
fun printMultiplicationProcess(lhv: Int, rhv: Int, outputName: String) {
    val wrtr = File(outputName).bufferedWriter()
    var sas = rhv
    var thn = 1
    val list = mutableListOf<Int>()
    while (sas != 0) {
        list.add(lhv * (sas % 10) * thn)
        sas /= 10
        thn *= 10
    }
    val res = list.sum()
    wrtr.write(" ".repeat(digitNumber(res) + 1 - digitNumber(lhv)) + lhv + "\n")
    wrtr.write("*" + " ".repeat(digitNumber(res) - digitNumber(rhv)) + rhv + "\n")
    wrtr.write("-".repeat(digitNumber(res) + 1) + "\n")
    thn = 1
    for (i in 0 until list.size) {
        var str = "+"
        if (i == 0) str = " "
        str += " ".repeat(digitNumber(res) - digitNumber(list[i])) + list[i] / thn
        wrtr.write(str + "\n")
        thn *= 10
    }
    wrtr.write("-".repeat(digitNumber(res) + 1) + "\n")
    wrtr.write(" $res\n")
    wrtr.close()
}


/**
 * Сложная
 *
 * Вывести в выходной файл процесс деления столбиком числа lhv (> 0) на число rhv (> 0).
 *
 * Пример (для lhv == 19935, rhv == 22):
19935 | 22
-198     906
----
13
-0
--
135
-132
----
3

 * Используемые пробелы, отступы и дефисы должны в точности соответствовать примеру.
 *
 */
fun printDivisionProcess(lhv: Int, rhv: Int, outputName: String) {
    TODO()
}

