package ua.softserve.booklibrary.arquillian;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.persistence.ShouldMatchDataSet;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.jboss.arquillian.testng.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ua.softserve.booklibrary.bean.AuthorAction;
import ua.softserve.booklibrary.bean.filter.BooksFilteringBean;
import ua.softserve.booklibrary.bean.sort.MainSortingBean;
import ua.softserve.booklibrary.dao.facade.AuthorFacade;
import ua.softserve.booklibrary.dao.facade.impl.AuthorFacadeImpl;
import ua.softserve.booklibrary.dao.home.AuthorHome;
import ua.softserve.booklibrary.dao.home.impl.AuthorHomeImpl;
import ua.softserve.booklibrary.entity.Author;
import ua.softserve.booklibrary.exception.LibraryException;
import ua.softserve.booklibrary.manager.AuthorManager;
import ua.softserve.booklibrary.rest.client.AuthorClientService;
import ua.softserve.booklibrary.rest.service.AuthorService;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.File;
import java.util.ArrayList;

import static org.mockito.Mockito.mock;


public class AuthorTest extends Arquillian {

	@EJB
	private AuthorHome authorHome;

	@EJB
	private AuthorFacade authorFacade;

	@EJB
	private AuthorManager authorManager;

/*
	@PersistenceContext
	protected EntityManager em;

	@EJB
	private AuthorService authorService;
*/

	@Inject
	private AuthorClientService authorClientService;

	@Deployment
	public static Archive<?> createTestArchive() {

		WebArchive war = ShrinkWrap.create(WebArchive.class, "test.war");

		File[] richfacesLibs = Maven.resolver().loadPomFromFile("pom.xml").importRuntimeDependencies()
				.resolve("org.richfaces:richfaces:4.5.10.Final")
				.withTransitivity().asFile();

/*
		File[] mocktoLibs = Maven.resolver().loadPomFromFile("pom.xml").importRuntimeDependencies()
				.resolve("org.mockito:mockito-all:1.10.19")
				.withTransitivity().asFile();
*/

		war.addPackages(true, Author.class.getPackage());
		war.addPackages(true, AuthorHome.class.getPackage());
		war.addPackages(true, AuthorFacade.class.getPackage());
		war.addPackages(true, AuthorManager.class.getPackage());
		war.addPackages(true, AuthorAction.class.getPackage());
		war.addPackages(true, AuthorClientService.class.getPackage());
		war.addPackages(true, AuthorService.class.getPackage());
		war.addClass(MainSortingBean.class);
		war.addClass(BooksFilteringBean.class);
		war.addClass(LibraryException.class);
		war.addAsLibraries(richfacesLibs);
/*
		war.addAsLibraries(mocktoLibs);
*/
		war.addAsResource("test-persistence.xml", "META-INF/persistence.xml");
		war.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");

		System.out.println(war.toString(true));
		return war;
	}

/*
		@BeforeClass
		public void setUp() {
			em = mock(EntityManager.class);
			MockitoAnnotations.initMocks(this);
		}
*/

	@Test
	@UsingDataSet("dataset/initial-authors-dataset.xml")
	public void authorRestTest() {
		//Create new Author
		Author author = new Author();
		author.setFirstName("TestFirstName");
		author.setSecondName("TestSecondName");
/*
		authorHome.setEm(em);
*/
		//Persist new Author by Rest WS
//		authorClientService.saveAuthor(author);

		authorManager.save(author);
		//Create another Author
		Author anotherAuthor = new Author();
		anotherAuthor.setFirstName("TestAnotherFirstName");
		anotherAuthor.setSecondName("TestAnotherSecondName");
		//Persist another Author by Rest WS
		authorClientService.saveAuthor(anotherAuthor);

		//Check count Author entities in database
		Assert.assertEquals(3, authorFacade.findAll().size());
	}

/*
	@Test
	public void authorDAOTest(){
		//Create new Author
		Author author = new Author();
		author.setFirstName("TestFirstName");
		author.setSecondName("TestSecondName");
		//Persist new Author
		Author result = authorHome.save(author);
		//Check id after persist
		Assert.assertNotNull(result.getId());

		//Create another Author
		Author anotherAuthor = new Author();
		anotherAuthor.setFirstName("TestAnotherFirstName");
		anotherAuthor.setSecondName("TestAnotherSecondName");
		//Persist another Author
		authorHome.save(anotherAuthor);

		//Check count Author entities in database
		Assert.assertEquals(2, authorFacade.findAll().size());
	}
*/

/*
	@Test
	@UsingDataSet("dataset/initial-authors-dataset.xml")
	@ShouldMatchDataSet(value = "dataset/after-save-authors-dataset.xml",
			excludeColumns = {"ID", "CREATE_DATE"},
			orderBy = {"AUTHOR.FIRST_NAME"})
	public void saveAuthorTest() throws Exception {
		Author author = new Author();
		author.setFirstName("John");
		author.setSecondName("Cena");
		authorHome.save(author);
	}
*/
}