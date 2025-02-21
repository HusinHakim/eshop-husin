
web url: https://vivacious-hawk-husin-7fa43535.koyeb.app/

# Perbaikan Isu Kode yang Dilakukan

### Perbaikan Nama View di Workflow CI/CD
Saya menemukan bahwa error pada workflow CI/CD disebabkan oleh perbedaan penamaan view. Pada method `createProductPage`, sebelumnya nilai yang dikembalikan adalah `"createProduct"`, yang tidak sesuai dengan nama file view yang ada (terutama di lingkungan yang case sensitive seperti Linux). Saya mengubahnya menjadi `"CreateProduct"`, sehingga memastikan halaman yang dimaksud dapat diakses dengan benar.

### Pengembalian Nilai Tanpa Variabel Lokal
Di dalam kelas `ProductServiceImpl`, terdapat variabel lokal yang hanya berfungsi untuk menyimpan nilai sebelum langsung dikembalikan. Karena langkah tersebut tidak menambah manfaat atau kejelasan, saya menghapus variabel tersebut dan langsung mengembalikan nilai. Langkah ini membuat kode menjadi lebih ringkas dan efisien.

# Kriteria untuk Memenuhi Definisi Continuous Integration dan Continuous Deployment

## Continuous Integration (CI)
- **Automated Testing**:  
  File `ci.yml` memastikan bahwa setiap push atau pull request secara otomatis menjalankan unit test, sehingga kesalahan dapat dideteksi sejak dini.

- **Supply Chain Security**:  
  File `scorecard.yml` menjalankan pemeriksaan keamanan terhadap rantai pasokan repository, memastikan bahwa semua dependensi dan komponen pihak ketiga aman.

- **Standardized Environment**:  
  Seluruh proses testing dijalankan dalam lingkungan standar, misalnya menggunakan Java 21 pada Ubuntu, sehingga memastikan konsistensi hasil testing di setiap commit.

## Continuous Deployment (CD)
- **Autodeployment**:  
  Setiap kali terjadi pembaruan pada branch utama (`main`), aplikasi secara otomatis di-deploy ke Koyeb, sehingga fitur baru dan perbaikan bug dapat segera tersedia di lingkungan produksi.

- **Fail-Safe Deployment**:  
  Proses deployment akan dihentikan jika terdapat kegagalan pada unit test atau analisis kode, sehingga kode yang bermasalah tidak akan ter-deploy ke produksi.

- **Consistency and Reliability**:  
  Setiap commit memicu rangkaian langkah otomatis yang sama, mengeliminasi kesalahan manual. Penggunaan alat seperti Scorecard dan SonarCloud memastikan bahwa setiap perubahan kode telah memenuhi standar keamanan dan kualitas yang ditetapkan.
