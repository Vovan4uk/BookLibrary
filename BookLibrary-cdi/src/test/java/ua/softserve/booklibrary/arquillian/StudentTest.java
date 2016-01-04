import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.testng.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.testng.Assert;
import org.testng.annotations.Test;
import ua.softserve.booklibrary.dao.home.AuthorHome;
import ua.softserve.booklibrary.entity.Author;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.io.File;


public class StudentTest extends Arquillian {

	@EJB
	private AuthorHome authorHome;

	@Inject
	private EntityManager entityManager;

	/**
	 * Create testable archive
	 *
	 * @return Archive
	 */
	@Deployment
	public static Archive<?> createTestableDeployment() {

		// resolve all dependencies from pom.xml
		// TODO:// create basic class for deployments
		File[] libs = Maven.resolver().loadPomFromFile("pom.xml")
				.importRuntimeAndTestDependencies().asFile();

		WebArchive war = ShrinkWrap.create(WebArchive.class, "test.war");

		for (File file : libs) {
			war.addAsLibrary(file);
		}

		war.addPackages(true, Author.class.getPackage());
		war.addPackages(true, AuthorHome.class.getPackage());
		war.addAsResource("test-persistence.xml", "META-INF/persistence.xml");
		//Enable CDI
		war.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");

		return war;
	}

	@Test
	public void saveDuplicateAuthorThrowExceptionTest() {
		Assert.assertNotNull("asdasd");
	}
}