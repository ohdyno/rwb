package ch15.catalog;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/* ///////////////////////////////////////////////
 * PATH 1 - one possible design - catalog gets query
 * ///////////////////////////////////////////////
 */

public class CatalogTest {

  Catalog catalog;

  @BeforeEach
  public void setUp() {
    catalog = new Catalog();
    catalog.add("Hammer - 10 lb");
    catalog.add("shirt - XL - blue");
    catalog.add("shirt - L - green");
    catalog.add("Halloween candle - orange");
    catalog.add("Halloween candy - gum");
  }

  @Test
  public void testSimpleQuery() {
    final List<Item> list = catalog.itemsMatching("shirt");
    assertEquals(2, list.size());
  }

  // An additional test
  @Test
  public void testOrQuery() {
    final Query query = new OrQuery(new StringQuery("shirt"), new StringQuery("Halloween"));
    final List<Item> list = catalog.itemsMatching(query);
    assertEquals(4, list.size());
  }

	/*
   * ///////////////////////////////////////////////
	 * PATH 2 - another design - query gets catalog
	 * ///////////////////////////////////////////////
	 */

  @Test
  public void testQueryMatchInCatalog() {
    final Query query = new StringQuery("shirt");
    final List<Item> list = query.matchesIn(catalog);
    assertEquals(2, list.size());
  }
}