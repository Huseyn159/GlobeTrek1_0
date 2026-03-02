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


<img width="486" height="712" alt="Screenshot 2025-11-26 203338" src="https://github.com/user-attachments/assets/78416774-8549-4142-914b-df417cad584a" />


<img width="562" height="921" alt="Screenshot 2025-11-26 203400" src="https://github.com/user-attachments/assets/ed344788-70d0-4681-a635-d12ea70fa959" />
<img width="1325" height="891" alt="Screenshot 2025-11-26 203436" src="https://github.com/user-attachments/assets/46740af3-05c2-49dc-ba38-d85af44d4655" />
<img width="2530" height="1459" alt="Screenshot 2025-11-26 203534" src="https://github.com/user-attachments/assets/0d12e8ee-b75f-4624-a438-ec36287224b4" />
<img width="862" height="675" alt="Screenshot 2025-11-26 203552" src="https://github.com/user-attachments/assets/673416f8-5367-4e54-b586-b2fb47ff7db3" />
<img width="852" height="670" alt="Screenshot 2025-11-26 203621" src="https://github.com/user-attachments/assets/0d8823cd-35e9-4d97-b915-89c84d5de4e6" />
<img width="735" height="410" alt="Screenshot 2025-11-26 203631" src="https://github.com/user-attachments/assets/cdc89895-7799-4bf9-aff5-a14c526bf684" />

<img width="2516" height="1420" alt="Screenshot 2025-11-26 203656" src="https://github.com/user-attachments/assets/9d12bdf0-007f-4787-91e6-0e933d9fbb75"<img width="2523" height="1443" alt="Screenshot 2025-11-26 203709" src="https://github.com/user-attachments/assets/3ef709fb-33ec-4258-8e59-843415eec8a1" />
 /><img width="1664" height="1457" alt="Screenshot 2025-11-26 203800" src="https://github.com/user-attachments/assets/d3557c58-9b14-42f4-bbcf-33d828ff6db5" />
<img width="2519" height="1468" alt="Screenshot 2025-11-26 203820" src="https://github.com/user-attachments/assets/e7193661-7f49-4d17-8f1e-672c3316b022" />
<img width="823" height="1201" alt="Screenshot 2025-11-26 203906" src="https://github.com/user-attachments/assets/6c1fef7b-78ca-4422-b2a9-185ccce5727e" />

