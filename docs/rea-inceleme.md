# REA ön inceleme durumu

- REA 6.7.0 `inspect-artifact` ile New Star Soccer 4.32 temel APK paketi incelendi. Bu yalnızca statik paket envanteridir, oyun davranışının veya kaynak kodun tam analizi değildir.
- XAPK içinde bir temel APK ve 18 yapılandırma APK'sı bulunur. Paket adı `com.newstargames.newstarsoccer`, sürümü 4.32'dir.
- Temel APK envanterinde dört DEX dosyası ve 2.319 ZIP girdisi görüldü. Bu sayılar tek başına oyun mekaniğini açıklamaz.
- REA Android paket incelemesi, yapılandırılmış `jadx-headless-mcp` 0.7.1 JAR'ı bulunmadığı için çalışmadı. JDK ve bu analiz sağlayıcısı kurulmadan ayrıntılı decompile yapılamaz.
- World Soccer Champs XAPK'sı 237.511.212 bayttır; kullanılabilir Drive indirme aracının 100 MB sınırını aşıyor. İkinci paket bu nedenle incelenmedi.
- New Star Soccer XAPK dosyası `packages/` altında depoya eklendi. World Soccer Champs paketi henüz aktarılmadı.
