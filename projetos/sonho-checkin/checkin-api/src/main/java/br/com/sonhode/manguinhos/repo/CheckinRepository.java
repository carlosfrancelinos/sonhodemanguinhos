
package br.com.sonhode.manguinhos.repo;

import br.com.sonhode.manguinhos.domain.Checkin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckinRepository extends JpaRepository<Checkin, Long> {}
