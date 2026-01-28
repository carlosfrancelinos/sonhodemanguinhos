
package br.com.sonhode.manguinhos.repo;

import br.com.sonhode.manguinhos.domain.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestRepository extends JpaRepository<Guest, Long> {}
