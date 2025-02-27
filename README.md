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

# Implementasi Prinsip SOLID

## 1. Prinsip SOLID dalam Proyek Saya

Dalam pembuatan aplikasi e-shop ini, saya telah menerapkan prinsip SOLID untuk membuat kode yang lebih mudah dipelihara dan dikembangkan. Berikut penjelasan saya:

### Single Responsibility Principle (SRP)
Saya menerapkan SRP dengan memisahkan kelas-kelas berdasarkan tanggung jawabnya. Contohnya:

- `Car.java` hanya fokus menyimpan data mobil saja
  ```java
  public class Car {
      private String carId;
      private String carName;
      private String carColor;
      private int carQuantity;
      // Getter dan setter
  }
  ```
  
- `CarRepository.java` khusus menangani penyimpanan data
  ```java
  @Repository
  public class CarRepository implements ICarRepository {
      private final List<Car> carData = new ArrayList<>();
      
      @Override
      public Car createCar(Car car) {
          if (car.getCarId() == null) {
              UUID uuid = UUID.randomUUID();
              car.setCarId(uuid.toString());
          }
          carData.add(car);
          return car;
      }
      // Metode-metode penyimpanan lainnya
  }
  ```

- `CarServiceImpl.java` berisi logika bisnis saja
- `CarController.java` hanya mengurus routing dan permintaan HTTP

Pemisahan ini membuat kode saya lebih rapi dan mudah dimengerti. Jika ada perubahan pada cara penyimpanan data, saya hanya perlu mengubah repository tanpa mengganggu lapisan lain.

### Open-Closed Principle (OCP)

Saya menerapkan OCP dengan membuat interface dan dependency injection. Contohnya:

```java
public interface ICarRepository {
    Car createCar(Car car);
    Iterator<Car> findAll();
    Car findById(String id);
    Car update(String id, Car updatedCar);
    void delete(String id);
}
```

Dengan interface ini, saya bisa membuat beberapa implementasi berbeda tanpa mengubah kode yang sudah ada. Misalnya, saat nanti aplikasi perlu menyimpan data ke database, saya tinggal membuat kelas baru yang mengimplementasikan interface yang sama, tanpa perlu mengubah kode service atau controller.

### Liskov Substitution Principle (LSP)

LSP saya terapkan dengan memastikan implementasi kelas turunan bisa menggantikan interface induknya. Contoh nyata dalam kode saya:

```java
// CarServiceImpl bisa menggantikan CarService
@Service
public class CarServiceImpl implements CarService {
    @Override
    public List<Car> findAll() {
        Iterator<Car> carIterator = carRepository.findAll();
        List<Car> allCar = new ArrayList<>();
        carIterator.forEachRemaining(allCar::add);
        return allCar;
    }
    // Metode lainnya
}
```

Dengan ini, jika ada bagian kode yang menggunakan `CarService`, saya bisa mengganti implementasinya dengan `CarServiceImpl` tanpa masalah.

### Interface Segregation Principle (ISP)

Saya membuat interface yang fokus dan tidak terlalu besar. Misalnya:

- `ICarRepository` hanya berisi metode yang berhubungan dengan operasi database untuk entitas Car
- `CarService` hanya berisi operasi-operasi yang dibutuhkan untuk fungsionalitas mobil

Saya menghindari membuat interface "serba bisa" yang akhirnya memaksa implementasi kelas untuk metode yang tidak relevan baginya.

### Dependency Inversion Principle (DIP)

Saya menerapkan DIP dengan membuat kelas tingkat tinggi bergantung pada abstraksi, bukan implementasi konkrit. Contohnya:

```java
public class CarController {
    private final CarService carService; // Bergantung pada interface, bukan CarServiceImpl

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }
    // Metode lainnya
}
```

Dengan begini, `CarController` tidak perlu tahu implementasi spesifik dari `CarService` yang digunakan, yang penting interface-nya sesuai.

## 2. Keuntungan Menerapkan SOLID

### Kode Lebih Mudah Dimaintain
Pengalaman saya, ketika ada perubahan requirement, saya hanya perlu mengubah bagian kecil dari kode. Misalnya ketika perlu menambahkan validasi mobil, saya cukup mengubah `CarServiceImpl` tanpa menyentuh bagian lain.

### Testing Lebih Mudah
Saya bisa unit test setiap komponen secara terpisah. Contohnya:

```java
// Bisa menguji CarService tanpa CarRepository sungguhan
void testCarService() {
    // Buat mock repository untuk testing
    ICarRepository mockRepo = mock(ICarRepository.class);
    when(mockRepo.findById("mobil-1")).thenReturn(new Car());
    
    // Tinggal pakai mock repository
    CarService carService = new CarServiceImpl(mockRepo);
    
    // Test service tanpa database sungguhan
    Car hasilnya = carService.findById("mobil-1");
    assertNotNull(hasilnya);
}
```

### Kode Lebih Fleksibel
Saya bisa dengan mudah menambah fitur baru tanpa mengubah kode lama. Misalnya, saat menambahkan fitur penyewaan mobil, saya cukup membuat interface dan implementasi baru tanpa mengubah fitur yang sudah ada.

### Kolaborasi Tim Lebih Lancar
Dengan memisahkan tanggung jawab, anggota tim bisa bekerja di bagian berbeda tanpa bentrok. Seseorang bisa mengurus UI di Controller, sementara yang lain fokus di bagian Repository.

## 3. Masalah Jika Tidak Menerapkan SOLID

### Kode Jadi Sulit Diubah
Tanpa SOLID, perubahan kecil bisa merembet kemana-mana. Bayangkan jika saya menggabungkan logic database dan bisnis seperti ini:

```java
// Contoh kode buruk yang mencampur semua tanggung jawab
public class CarManager {
    private List<Car> cars = new ArrayList<>();
    
    public void addCar(Car car) {
        // Generate ID, validasi, dan simpan semuanya dicampur
        car.setCarId(UUID.randomUUID().toString());
        
        if (car.getCarName() == null) {
            throw new IllegalArgumentException("Nama mobil harus diisi");
        }
        
        cars.add(car);
        
        // Logic UI juga dicampur disini
        System.out.println("Mobil " + car.getCarName() + " berhasil ditambahkan");
    }
    
    // Metode lain yang juga mencampur banyak tanggung jawab
}
```

Kode seperti ini sangat susah diubah. Ketika ingin mengubah cara penyimpanan data, saya juga harus ubah logika bisnis dan UI.

### Testing Jadi Sulit
Tanpa SOLID, saya harus test semua fungsi sekaligus karena semuanya terhubung erat. Tidak bisa test secara terisolasi.

### Kode Jadi Sulit Dipahami
Tanpa pemisahan tanggung jawab, kode jadi dibaca dan dipahami orang lain. Ini bikin onboarding anggota tim baru jadi lebih susah.

---

Dengan menerapkan prinsip SOLID, saya merasakan langsung manfaatnya dalam pengembangan proyek ini. Kode jadi lebih terstruktur, mudah dipelihara, dan siap untuk dikembangkan lebih lanjut di masa depan.
