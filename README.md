# Exam Checker - فاحص الاختبارات  

## 🚀 الوصف

"Exam Checker" هو مشروع بلغة Java يُساعد في مقارنة الإجابات الصحيحة بإجابات الطالب، مع دعم ميزات متقدمة مثل:

- مقارنة إجابات الطلاب

- حساب الدرجات بدقة

- دعم أوزان مختلفة للأسئلة

- إدارة بيانات الطلاب

---

## 🛠️ المزايا الجديدة

- مقارنة إجابات الطالب مع الإجابات الصحيحة

- عرض عدد الإجابات الصحيحة والخاطئة

- دعم أوزان مختلفة للأسئلة

- إدارة بيانات متعددة للطلاب

- تخصيص خيارات الإجابات

- وضع "Wrong versus Right" لتعديل نقاط التصحيح

- تحديد أسئلة دائمة الصحة

---

## 📊 كيفية الاستخدام

1. الدخول الى المجلد الذي تريد اضافة المكتبة اليه:

2. استنساخ المشروع:

   ```bash

   git clone https://github.com/Tati-I/ExamChecker.git

   ```

3. الدخول الى ال class الخاص بالمشروع:

   ```bash

   cd ExamChecker\src\corrector\exam

   ```

4. عمل import داخل الكود الخاص بك:

   ```java

   import corrector.exam.ExamChecker;

   import corrector.exam.Student; // للتعامل مع بيانات الطلاب

   ```

---

### الوظائف (Methods) الرئيسية:

1. إدارة الإجابات والطلاب:

   ```java

  // تحديد الإجابات الصحيحة

   examChecker.setCorrectAnswers(List.of('A', 'B', 'C', 'D'));

   

  // تحديد إجابات الطالب

   examChecker.setStudentAnswers(List.of('A', 'B', 'C', 'A'));

   

  // إضافة طالب

   Student student = new Student("احمد", "12345", List.of('A', 'B', 'C', 'A'));

   examChecker.addStudent(student);

   ```

2. خصائص متقدمة:

   ```java

  // تعيين وزن لأسئلة محددة

   examChecker.setQuestionWeight(0, 1.5); // السؤال الأول له وزن أكبر

   

  // تحديد خيارات الإجابات المقبولة

   examChecker.setValidOptions(List.of('A', 'B', 'C', 'D', 'E'));

   

  // تحديد سؤال كصحيح دائمًا

   examChecker.setAlwaysCorrectQuestion(2); // السؤال الثالث دائمًا صحيح

   ```

3. حساب الدرجات:

   ```java

  // تحديد الدرجة الكاملة

   examChecker.setMaxScore(100);

   

  // الحصول على عدد الإجابات الصحيحة والخاطئة

   int correctCount = examChecker.getCorrectCount();

   int incorrectCount = examChecker.getIncorrectCount();

   

  // حساب الدرجة النهائية

   double score = examChecker.getScore();

   ```

4. ميزات إضافية:

   ```java

  // عرض نتائج جميع الطلاب

   String allScores = examChecker.getStudentsScores();

   

  // البحث عن طالب برقمه

   Student specificStudent = examChecker.getStudentByID("12345");

   ```

---

## 🔧 البيئة المطلوبة

- Java 8 أو أحدث

- بيئة تطوير مثل IntelliJ IDEA أو Eclipse

---

## 🏆 كيف يعمل المشروع؟

1. إدخال الإجابات الصحيحة وإجابات الطلاب

2. تطبيق الإعدادات المتقدمة (الأوزان، الأسئلة الثابتة)

3. حساب الدرجات بدقة

4. عرض النتائج التفصيلية

---

## 👥 المساهمون

- ابراهيم مخللاتي - المطور الرئيسي

## 📝 ملاحظات

- تأكد من استيراد الفئات ExamChecker و Student

- يمكن تخصيص آلية التصحيح بشكل كامل
