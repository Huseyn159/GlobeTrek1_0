TravelApp — Java ilə yazılmış sadə səyahət idarəetmə proqramıdır. Məlumatlar JSON olmadan, sadəcə Java I/O (FileReader, FileWriter, BufferedReader, BufferedWriter) vasitəsilə .txt fayllarında saxlanılır.

🔐 Login & Register

İstifadəçilər yeni hesab yarada və mövcud hesabla daxil ola bilirlər. Bütün user məlumatları faylda saxlanır və giriş zamanı fayldan oxunur.

💳 Add Balance

İstifadəçi balans əlavə edir. Balans yeniləndikdən sonra dərhal .txt faylına yazılır.

🔢 Luhn Alqoritmi

Balans artırmaq üçün daxil edilən kart nömrəsinin düz olub-olmaması Luhn üsulu ilə yoxlanılır.

✈️ Travel Booking

İstifadəçi uçuş seçib bron edə bilir. Bron zamanı:

balansdan məbləğ çıxılır,

səyahət travel_history.txt-ə yazılır,

istifadəçiyə XP verilir və level yenilənir.

🏆 Achievements

Sistem istifadəçinin fəaliyyətinə görə avtomatik achievement açır (məs. 1-ci uçuş, 3 uçuş və s.). Açılan achievement-lər həm yaddaşa yazılır, həm də ekranda göstərilir.

👤 View Profile

İstifadəçinin:

username

balance

level

XP

achievements
şəxsi məlumatları göstərilir.

📜 Travel History

İstifadəçinin keçmiş səyahətləri fayldan oxunaraq siyahı şəklində ekrana verilir.

❓ Visa Quiz

Sadə sual-cavab sistemi. Doğru cavab visa pass verir.

🛠 İstifadə olunan texnologiya və məntiq

Java SE

JavaFX (UI variantında)

File I/O ilə məlumat saxlama

OOP prinsipləri: Service layer, Model classes, Manager classes

Luhn alqoritmi kart yoxlama üçün

XP sistemi + Level sistemi

Achievement Manager davranışa görə badge açır



--------------------
SCREENSHOTS
--------------------





