package guru.springframework.repositories;

import guru.springframework.domain.UnitOfMeasure;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UnitOfMeasureRepositoryIT {

	@Autowired
	private UnitOfMeasureRepository sut;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void findByDescription() {
		Optional<UnitOfMeasure> uom = sut.findByDescription("Teaspoon");

		Assert.assertTrue(uom.isPresent());
		Assert.assertEquals("Teaspoon", uom.get().getDescription());
	}

	@Test
	public void findByDescriptionCup() {
		Optional<UnitOfMeasure> uom = sut.findByDescription("Cup");

		Assert.assertTrue(uom.isPresent());
		Assert.assertEquals("Cup", uom.get().getDescription());
	}
}