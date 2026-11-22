import { useState, useEffect } from 'react';
import { COURSE_CATEGORIES } from '../../utils/categories';
import { getCatalogCourses } from '../../api/catalogApi';
import styles from './HomePage.module.scss';

const VISIBLE_CATEGORIES_COUNT = 5;
const INITIAL_COURSES_LIMIT = 10;
const COURSES_LOAD_STEP = 10;

function HomePage() {
  const [courses, setCourses] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [selectedCategoryTag, setSelectedCategoryTag] = useState(null);
  const [startIndex, setStartIndex] = useState(0);
  const [visibleCoursesCount, setVisibleCoursesCount] = useState(INITIAL_COURSES_LIMIT);

  useEffect(function fetchCoursesOnMount() {
    async function loadCourses() {
      try {
        setIsLoading(true);
        const data = await getCatalogCourses();
        setCourses(Array.isArray(data) ? data : []);
      } catch (error) {
        console.error('Ошибка при загрузке каталога курсов:', error);
        setCourses([]);
      } finally {
        setIsLoading(false);
      }
    }

    loadCourses();
  }, []);

  function handleJoinClick() {
    console.log('Открыть модальное окно авторизации');
  }

  function handleScrollToCourses() {
    const section = document.getElementById('courses-section');
    if (section) {
      section.scrollIntoView({ behavior: 'smooth' });
    }
  }

  function handlePrevCategories() {
    setStartIndex(function updatePrevIndex(prevIndex) {
      return (prevIndex - 1 + COURSE_CATEGORIES.length) % COURSE_CATEGORIES.length;
    });
  }

  function handleNextCategories() {
    setStartIndex(function updateNextIndex(prevIndex) {
      return (prevIndex + 1) % COURSE_CATEGORIES.length;
    });
  }

  function handleCategoryClick(event) {
    const tag = event.currentTarget.dataset.tag;
    if (!tag) {
      return;
    }

    setVisibleCoursesCount(INITIAL_COURSES_LIMIT);

    setSelectedCategoryTag(function toggleCategory(prevTag) {
      return prevTag === tag ? null : tag;
    });
  }

  function handleDotClick(event) {
    const index = Number(event.currentTarget.dataset.index);
    setStartIndex(index);
  }

  function handleLoadMoreCourses() {
    setVisibleCoursesCount(function increaseLimit(prevCount) {
      return prevCount + COURSES_LOAD_STEP;
    });
  }

  function getVisibleCategories() {
    const result = [];
    for (let i = 0; i < VISIBLE_CATEGORIES_COUNT; i += 1) {
      const categoryIndex = (startIndex + i) % COURSE_CATEGORIES.length;
      result.push(COURSE_CATEGORIES[categoryIndex]);
    }
    return result;
  }

  function getCoursesCountByCategory(categoryTag) {
    return courses.filter(function matchTag(course) {
      const courseTag = course.tag || course.category;
      return courseTag === categoryTag;
    }).length;
  }

  function formatCourseCount(count) {
    const mod10 = count % 10;
    const mod100 = count % 100;

    if (mod10 === 1 && mod100 !== 11) {
      return `${count} курс`;
    }
    if (mod10 >= 2 && mod10 <= 4 && (mod100 < 10 || mod100 >= 20)) {
      return `${count} курса`;
    }
    return `${count} курсов`;
  }

  function getSelectedCategoryTitle() {
    const found = COURSE_CATEGORIES.find(function findByTag(category) {
      return (category.tag || category.id) === selectedCategoryTag;
    });
    return found ? found.title || found.name : '';
  }

  const visibleCategories = getVisibleCategories();

  const filteredCourses = selectedCategoryTag
    ? courses.filter(function filterBySelectedTag(course) {
        const courseTag = course.tag || course.category;
        return courseTag === selectedCategoryTag;
      })
    : [];

  const displayedCourses = filteredCourses.slice(0, visibleCoursesCount);
  const hasMoreCourses = visibleCoursesCount < filteredCourses.length;

  function renderCoursesContent() {
    if (isLoading) {
      return <p className={styles.emptyMessage}>Идет загрузка курсов</p>;
    }

    if (!selectedCategoryTag) {
      return <p className={styles.emptyMessage}>Выберите категорию</p>;
    }

    return (
      <>
        <h2 className={styles.coursesTitle}>{getSelectedCategoryTitle()}</h2>

        {filteredCourses.length === 0 ? (
          <p className={styles.emptyMessage}>Курсов в данной категории нет</p>
        ) : (
          <>
            <div className={styles.coursesGrid}>
              {displayedCourses.map(function renderCourseCard(course) {
                const formattedPrice = course.price ? `${course.price} р.` : 'Бесплатно';

                return (
                  <article key={course.id} className={styles.courseCard}>
                    <h3 className={styles.courseTitle}>{course.title}</h3>
                    <span className={styles.coursePrice}>{formattedPrice}</span>
                  </article>
                );
              })}
            </div>

            {hasMoreCourses && (
              <div className={styles.loadMoreWrapper}>
                <button
                  type="button"
                  className={styles.loadMoreBtn}
                  onClick={handleLoadMoreCourses}
                >
                  Смотреть еще
                </button>
              </div>
            )}
          </>
        )}
      </>
    );
  }

  return (
    <div className={styles.homePage}>
      <section className={styles.hero}>
        <div className={styles.heroContent}>
          <h1 className={styles.heroTitle}>Платформа для онлайн обучения все в одном.</h1>
          <p className={styles.heroSubtitle}>
            Курсы, практика, и сертификаты — <br />
            всё для твоего роста.
          </p>
          <div className={styles.heroButtons}>
            <button type="button" className={styles.btnPrimary} onClick={handleJoinClick}>
              Присоединиться
            </button>
            <button type="button" className={styles.btnOutline} onClick={handleScrollToCourses}>
              Смотреть курсы
            </button>
          </div>
        </div>
      </section>

      <section id="courses-section" className={styles.categoriesSection}>
        <h2 className={styles.sectionTitle}>Курсы</h2>

        <div className={styles.carouselWrapper}>
          <button
            type="button"
            className={`${styles.arrowBtn} ${styles.arrowLeft}`}
            onClick={handlePrevCategories}
            aria-label="Предыдущие категории"
          >
            &#9664;
          </button>

          <div className={styles.categoriesTrack}>
            {visibleCategories.map(function renderCategoryCard(category) {
              const categoryTag = category.tag || category.id;
              const categoryTitle = category.title || category.name;
              const isSelected = selectedCategoryTag === categoryTag;
              const count =
                typeof category.count === 'number'
                  ? category.count
                  : getCoursesCountByCategory(categoryTag);

              const cardClassName = isSelected
                ? `${styles.categoryCard} ${styles.categoryCardActive}`
                : styles.categoryCard;

              return (
                <div
                  key={categoryTag}
                  data-tag={categoryTag}
                  className={cardClassName}
                  onClick={handleCategoryClick}
                >
                  <div className={styles.categoryCardTop}>
                    <span className={styles.courseCountBadge}>{formatCourseCount(count)}</span>
                    <h3 className={styles.categoryName}>{categoryTitle}</h3>
                  </div>
                </div>
              );
            })}
          </div>

          <button
            type="button"
            className={`${styles.arrowBtn} ${styles.arrowRight}`}
            onClick={handleNextCategories}
            aria-label="Следующие категории"
          >
            &#9654;
          </button>
        </div>

        <div className={styles.dots}>
          {COURSE_CATEGORIES.map(function renderDot(category, index) {
            const categoryTag = category.tag || category.id || index;
            const dotClassName =
              index === startIndex ? `${styles.dot} ${styles.dotActive}` : styles.dot;

            return (
              <button
                key={categoryTag}
                type="button"
                data-index={index}
                className={dotClassName}
                onClick={handleDotClick}
                aria-label={`Перейти к категории ${index + 1}`}
              />
            );
          })}
        </div>
      </section>

      <section className={styles.coursesSection}>{renderCoursesContent()}</section>
    </div>
  );
}

export default HomePage;
