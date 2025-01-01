package com.chiragbhisikar.Library.Management.System.Repository;

import com.chiragbhisikar.Library.Management.System.Model.Copy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CopyRepository extends JpaRepository<Copy, Long> {
    Optional<Copy> findByBookBarcodeNumber(String bookBarcodeNumber);

    Optional<Copy> findCopyByBookBarcodeNumber(String bookBarcodeNumber);

    boolean existsByBookBarcodeNumber(String bookBarcodeNumber);


}
