# REA ön inceleme durumu

- REA 6.7.0 `inspect-artifact` ile New Star Soccer 4.32 temel APK paketi incelendi. Bu yalnızca statik paket envanteridir, oyun davranışının veya kaynak kodun tam analizi değildir.
- XAPK içinde bir temel APK ve 18 yapılandırma APK'sı bulunur. Paket adı `com.newstargames.newstarsoccer`, sürümü 4.32'dir.
- Temel APK envanterinde dört DEX dosyası ve 2.319 ZIP girdisi görüldü. Bu sayılar tek başına oyun mekaniğini açıklamaz.
- REA Android paket incelemesi, yapılandırılmış `jadx-headless-mcp` 0.7.1 JAR'ı bulunmadığı için çalışmadı. JDK ve bu analiz sağlayıcısı kurulmadan ayrıntılı decompile yapılamaz.
- World Soccer Champs XAPK'sı 237.511.212 bayttır; kullanılabilir Drive indirme aracının 100 MB sınırını aşıyor. İkinci paket bu nedenle incelenmedi.
- New Star Soccer XAPK dosyası `packages/` altında depoya eklendi. World Soccer Champs paketi henüz aktarılmadı.

## Android analiz sağlayıcısı denemesi

REA 6.7.0 komut satırı kullanılabilir. Bu oturumda npm üzerinden Java 21 çalışma zamanı edinildi. REA'nın Android incelemesi için istediği `jadx-headless-mcp` 0.7.1 JAR'ı resmi GitHub sürümünden indirilmeye çalışıldı; sürüm dosyalarının bulunduğu sunucuya bu ortamda erişilemedi. Bu nedenle sağlayıcı kurulamadı ve Android kodu REA ile decompile edilmedi. REA'nın `inspect-artifact` çıktısı paket envanteriyle sınırlıdır; orijinal kaynak kodu veya davranış eşdeğerliğini doğrulamaz.

Birebir genişletme için projenin özgün kaynak kodu ve derleme yapılandırması (ya da inceleme için gerekli JADX sağlayıcısının erişilebilir bir kopyası) gereklidir. Yalnızca derlenmiş APK, eksiksiz orijinal kaynak kodunun geri kazanılacağını garanti etmez.

## Ek decompile denemesi

REA envanterine ek olarak, erişilebilir npm paketindeki bağımsız JADX 1.1.0 ve Java 21 ile temel APK decompile edilmeye çalışıldı. Bu, REA'nın JADX sağlayıcısı değildir. Çıktı geçici önbellekte tutuldu; 19.346 dosya üretildi ve araç 55 hata bildirdi. Paket içindeki sınıf adları maç (`bb_GSMatchUtility`, `bb_GSScreen_Match`), lig fikstürü (`bb_GSLeagueFixturesUtility`), beceriler (`bb_GSScreen_Skills`) ve sözleşme (`bb_GSContractUtility`) gibi alanlara işaret ediyor. Bu sınıf adları tek başına bunların tüm kurallarını veya eksiksiz çalışır kaynak kodunu ortaya koymaz. Üretilen kodun doğruluğu ve derlenebilirliği henüz doğrulanmadı; bazı sınıflarda decompile hataları mevcut.

## Wonderkid'deki devam çalışması

Sınıf envanterinde görülen sözleşme ve oyuncu kariyeri alanlarından hareketle mevcut özgün prototipe itibar, otomatik kulüp yükselmesi, maç maaşı ve üç gol bonusu eklendi. Bunlar birebir NSS kuralları veya kaynak kodunun aktarımı değildir; eşikler Wonderkid için yeni belirlenmiştir.
