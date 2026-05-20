-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 19 Bulan Mei 2026 pada 13.30
-- Versi server: 10.4.32-MariaDB
-- Versi PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_hostel_2_16`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `kamar`
--

CREATE TABLE `kamar` (
  `id` int(11) NOT NULL,
  `nomor_kamar` varchar(10) NOT NULL,
  `tipe` enum('Standard','Deluxe','Suite') DEFAULT 'Standard',
  `harga_per_malam` decimal(10,2) NOT NULL,
  `status` enum('Tersedia','Terisi','Maintenance') DEFAULT 'Tersedia',
  `fasilitas` text DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `kamar`
--

INSERT INTO `kamar` (`id`, `nomor_kamar`, `tipe`, `harga_per_malam`, `status`, `fasilitas`, `created_at`) VALUES
(2, '102', 'Standard', 150000.00, 'Terisi', 'AC, TV, Kamar Mandi Dalam', '2026-05-16 07:47:58'),
(3, '103', 'Standard', 150000.00, 'Tersedia', 'AC, TV, Kamar Mandi Dalam', '2026-05-16 07:47:58'),
(4, '104', 'Standard', 150000.00, 'Maintenance', 'AC, TV, Kamar Mandi Dalam', '2026-05-16 07:47:58'),
(5, '201', 'Deluxe', 250000.00, 'Tersedia', 'AC, TV LED, WiFi, Breakfast', '2026-05-16 07:47:58'),
(6, '202', 'Deluxe', 250000.00, 'Terisi', 'AC, TV LED, WiFi, Breakfast', '2026-05-16 07:47:58'),
(7, '203', 'Deluxe', 25000000.00, 'Terisi', 'AC, TV LED, WiFi, Breakfast', '2026-05-16 07:47:58'),
(8, '301', 'Suite', 40000000.00, 'Tersedia', 'AC, TV LED 42, WiFi, Living Room', '2026-05-16 07:47:58'),
(9, '302', 'Suite', 400000.00, 'Tersedia', 'AC, TV LED 42, WiFi, Living Room', '2026-05-16 07:47:58'),
(10, '303', 'Suite', 40000000.00, 'Maintenance', 'AC, TV LED 42, WiFi, Living Room', '2026-05-16 11:42:52'),
(12, '111', 'Standard', 15000000.00, 'Tersedia', 'AC, TV, Kamar Mandi Dalam', '2026-05-16 11:58:28');

-- --------------------------------------------------------

--
-- Stand-in struktur untuk tampilan `laporan_pendapatan`
-- (Lihat di bawah untuk tampilan aktual)
--
CREATE TABLE `laporan_pendapatan` (
`id` int(11)
,`nama_tamu` varchar(100)
,`nomor_kamar` varchar(10)
,`tipe` enum('Standard','Deluxe','Suite')
,`tanggal_check_in` date
,`tanggal_check_out` date
,`jumlah_malam` int(11)
,`total_harga` decimal(10,2)
,`status` enum('Booking','Check-In','Check-Out','Cancelled')
,`metode_pembayaran` enum('Cash','Transfer','Kartu Kredit')
,`status_pembayaran` enum('Lunas','Belum Lunas')
);

-- --------------------------------------------------------

--
-- Struktur dari tabel `pembayaran`
--

CREATE TABLE `pembayaran` (
  `id` int(11) NOT NULL,
  `id_reservasi` int(11) NOT NULL,
  `jumlah_bayar` decimal(10,2) DEFAULT NULL,
  `metode_pembayaran` enum('Cash','Transfer','Kartu Kredit') DEFAULT 'Cash',
  `tanggal_bayar` datetime DEFAULT current_timestamp(),
  `status` enum('Lunas','Belum Lunas') DEFAULT 'Belum Lunas'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `pembayaran`
--

INSERT INTO `pembayaran` (`id`, `id_reservasi`, `jumlah_bayar`, `metode_pembayaran`, `tanggal_bayar`, `status`) VALUES
(1, 1, 450000.00, 'Cash', '2026-05-16 14:47:59', 'Lunas'),
(2, 2, 750000.00, 'Transfer', '2026-05-16 14:47:59', 'Lunas'),
(4, 4, 750000.00, 'Transfer', '2026-05-16 14:47:59', 'Belum Lunas'),
(5, 5, 1600000.00, 'Kartu Kredit', '2026-05-16 14:47:59', 'Belum Lunas'),
(6, 6, 300000.00, 'Cash', '2026-05-16 14:47:59', 'Lunas'),
(7, 7, 500000.00, 'Transfer', '2026-05-16 14:47:59', 'Lunas');

-- --------------------------------------------------------

--
-- Struktur dari tabel `reservasi`
--

CREATE TABLE `reservasi` (
  `id` int(11) NOT NULL,
  `id_tamu` int(11) NOT NULL,
  `id_kamar` int(11) NOT NULL,
  `tanggal_check_in` date NOT NULL,
  `tanggal_check_out` date NOT NULL,
  `jumlah_malam` int(11) DEFAULT NULL,
  `total_harga` decimal(10,2) DEFAULT NULL,
  `status` enum('Booking','Check-In','Check-Out','Cancelled') DEFAULT 'Booking',
  `tanggal_booking` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `reservasi`
--

INSERT INTO `reservasi` (`id`, `id_tamu`, `id_kamar`, `tanggal_check_in`, `tanggal_check_out`, `jumlah_malam`, `total_harga`, `status`, `tanggal_booking`) VALUES
(1, 1, 2, '2026-05-10', '2026-05-13', 3, 450000.00, 'Check-Out', '2026-05-16 14:47:59'),
(2, 2, 6, '2026-05-15', '2026-05-18', 3, 75000000.00, 'Check-Out', '2026-05-16 14:47:59'),
(4, 4, 5, '2026-05-25', '2026-05-28', 3, 750000.00, 'Check-Out', '2026-05-16 14:47:59'),
(5, 5, 8, '2026-06-01', '2026-06-05', 4, 1600000.00, 'Check-Out', '2026-05-16 14:47:59'),
(6, 6, 3, '2026-05-12', '2026-05-14', 2, 300000.00, 'Check-Out', '2026-05-16 14:47:59'),
(7, 7, 7, '2026-05-18', '2026-05-20', 2, 500000.00, 'Check-Out', '2026-05-16 14:47:59'),
(9, 7, 7, '2026-05-19', '2026-05-20', 1, 250000.00, 'Check-In', '2026-05-19 18:27:39');

-- --------------------------------------------------------

--
-- Struktur dari tabel `tamu`
--

CREATE TABLE `tamu` (
  `id` int(11) NOT NULL,
  `nama` varchar(100) NOT NULL,
  `no_ktp` varchar(20) DEFAULT NULL,
  `no_telepon` varchar(15) DEFAULT NULL,
  `alamat` text DEFAULT NULL,
  `tanggal_lahir` date DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `tamu`
--

INSERT INTO `tamu` (`id`, `nama`, `no_ktp`, `no_telepon`, `alamat`, `tanggal_lahir`, `created_at`) VALUES
(1, 'Ahmad Rizki', '3201234567890123', '081234567890', 'Jl. Merdeka No. 123, Jakarta', '1990-05-15', '2026-05-16 07:47:58'),
(2, 'Dewi Lestari', '3201234567890124', '081234567891', 'Jl. Sudirman No. 45, Bandung', '1992-08-20', '2026-05-16 07:47:58'),
(3, 'Bambang Wijaya', '3201234567890125', '081234567892', 'Jl. Ahmad Yani No. 78, Surabaya', '1988-03-10', '2026-05-16 07:47:58'),
(4, 'Siti Rahayu', '3201234567890126', '081234567893', 'Jl. Diponegoro No. 12, Yogyakarta', '1995-12-05', '2026-05-16 07:47:58'),
(5, 'Eko Prasetyo', '3201234567890127', '081234567894', 'Jl. Gatot Subroto No. 56, Semarang', '1987-07-25', '2026-05-16 07:47:58'),
(6, 'Rina Susanti', '3201234567890128', '081234567895', 'Jl. Kartini No. 90, Malang', '1993-11-18', '2026-05-16 07:47:58'),
(7, 'Hendra Gunawan', '3201234567890129', '081234567896', 'Jl. Veteran No. 34, Denpasar', '1991-04-22', '2026-05-16 07:47:58'),
(8, 'Maya Anggraini', '3201234567890130', '081234567897', 'Jl. Pahlawan No. 67, Medan', '1994-09-30', '2026-05-16 07:47:58');

-- --------------------------------------------------------

--
-- Struktur dari tabel `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `nama_lengkap` varchar(100) DEFAULT NULL,
  `role` enum('admin','resepsionis') DEFAULT 'resepsionis',
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `users`
--

INSERT INTO `users` (`id`, `username`, `password`, `nama_lengkap`, `role`, `created_at`) VALUES
(2, 'resepsionis', 'rec123', 'Resepsionis Hostel', 'resepsionis', '2026-05-16 09:22:15'),
(3, 'ayii', '123', 'Fahri Hidayatullah', 'resepsionis', '2026-05-18 16:23:54');

-- --------------------------------------------------------

--
-- Struktur untuk view `laporan_pendapatan`
--
DROP TABLE IF EXISTS `laporan_pendapatan`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `laporan_pendapatan`  AS SELECT `r`.`id` AS `id`, `t`.`nama` AS `nama_tamu`, `k`.`nomor_kamar` AS `nomor_kamar`, `k`.`tipe` AS `tipe`, `r`.`tanggal_check_in` AS `tanggal_check_in`, `r`.`tanggal_check_out` AS `tanggal_check_out`, `r`.`jumlah_malam` AS `jumlah_malam`, `r`.`total_harga` AS `total_harga`, `r`.`status` AS `status`, `p`.`metode_pembayaran` AS `metode_pembayaran`, `p`.`status` AS `status_pembayaran` FROM (((`reservasi` `r` join `tamu` `t` on(`r`.`id_tamu` = `t`.`id`)) join `kamar` `k` on(`r`.`id_kamar` = `k`.`id`)) left join `pembayaran` `p` on(`r`.`id` = `p`.`id_reservasi`)) WHERE `r`.`status` = 'Check-Out' ORDER BY `r`.`tanggal_check_out` DESC ;

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `kamar`
--
ALTER TABLE `kamar`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `nomor_kamar` (`nomor_kamar`);

--
-- Indeks untuk tabel `pembayaran`
--
ALTER TABLE `pembayaran`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_reservasi` (`id_reservasi`);

--
-- Indeks untuk tabel `reservasi`
--
ALTER TABLE `reservasi`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_tamu` (`id_tamu`),
  ADD KEY `id_kamar` (`id_kamar`);

--
-- Indeks untuk tabel `tamu`
--
ALTER TABLE `tamu`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `no_ktp` (`no_ktp`);

--
-- Indeks untuk tabel `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `kamar`
--
ALTER TABLE `kamar`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT untuk tabel `pembayaran`
--
ALTER TABLE `pembayaran`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT untuk tabel `reservasi`
--
ALTER TABLE `reservasi`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT untuk tabel `tamu`
--
ALTER TABLE `tamu`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT untuk tabel `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Ketidakleluasaan untuk tabel pelimpahan (Dumped Tables)
--

--
-- Ketidakleluasaan untuk tabel `pembayaran`
--
ALTER TABLE `pembayaran`
  ADD CONSTRAINT `pembayaran_ibfk_1` FOREIGN KEY (`id_reservasi`) REFERENCES `reservasi` (`id`) ON DELETE CASCADE;

--
-- Ketidakleluasaan untuk tabel `reservasi`
--
ALTER TABLE `reservasi`
  ADD CONSTRAINT `reservasi_ibfk_1` FOREIGN KEY (`id_tamu`) REFERENCES `tamu` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `reservasi_ibfk_2` FOREIGN KEY (`id_kamar`) REFERENCES `kamar` (`id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
