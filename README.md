# Exam Checker - فاحص الاختبارات  

## 🚀 الوصف
"Exam Checker" هو مشروع بلغة Java يُساعد في مقارنة الإجابات الصحيحة بإجابات الطالب، مع حساب الإجابات الصحيحة والخاطئة، والعلامة النهائية من أصل 100 أو أي قيمة تحددها.

---

## 🛠️ المزايا
- مقارنة إجابات الطالب مع الإجابات الصحيحة.
- عرض عدد الإجابات الصحيحة والخاطئة.
- عرض مواقع الإجابات الخاطئة.
- حساب العلامة النهائية من أصل قيمة معينة (100 أو 500 أو غيرها).

---

## 📊 كيفية الاستخدام

1. **استنساخ المشروع:**
   ```bash
   git clone https://github.com/Tati-I/ExamChecker.git
2. **الدخول الى ال class الخاص بالمشروع:**
   ```bash
   cd ExamChecker\src\corrector\exam

---

### الوظائف (Methods) الرئيسية في ExamChecker

1. **setCorrectAnswers(List<Character> correctAnswers):**
   - **الوصف:** يحدد الإجابات الصحيحة للاختبار.
   - **المُدخلات:** قائمة من الإجابات الصحيحة (مثل `['A', 'B', 'C', 'D']`).
   - **الاستخدام:**
     ```java
     examChecker.setCorrectAnswers(List.of('A', 'B', 'C', 'D'));
     ```

2. **setStudentAnswers(List<Character> studentAnswers):**
   - **الوصف:** يحدد إجابات الطالب للاختبار.
   - **المُدخلات:** قائمة من إجابات الطالب (مثل `['A', 'B', 'C', 'A']`).
   - **الاستخدام:**
     ```java
     examChecker.setStudentAnswers(List.of('A', 'B', 'C', 'A'));
     ```

3. **getCorrectCount():**
   - **الوصف:** يعيد عدد الإجابات الصحيحة.
   - **الاستخدام:**
     ```java
     int correctCount = examChecker.getCorrectCount();
     System.out.println("الإجابات الصحيحة: " + correctCount);
     ```

4. **getIncorrectCount():**
   - **الوصف:** يعيد عدد الإجابات الخاطئة.
   - **الاستخدام:**
     ```java
     int incorrectCount = examChecker.getIncorrectCount();
     System.out.println("الإجابات الخاطئة: " + incorrectCount);
     ```

5. **toString():**
   - **الوصف:** يعرض تقريرًا كاملًا عن نتائج الاختبار.
   - **الاستخدام:**
     ```java
     System.out.println(examChecker.toString());
     ```

---

## 🔧 البيئة المطلوب
-Java 8 أو أحدث.
- أي بيئة تطوير مناسبة مثل IntelliJ IDEA أو Eclipse.

---

## 🏆 كيف يعمل المشروع؟
1. **تُدخل الإجابات الصحيحة وإجابات الطالب.**
2. **يُحسب عدد الإجابات الصحيحة والخاطئة.**
3. **تُعرض النتائج مع العلامة النهائية.**

---

## 👥 المساهمون
- ابراهيم مخللاتي - المطور الرئيسي.

