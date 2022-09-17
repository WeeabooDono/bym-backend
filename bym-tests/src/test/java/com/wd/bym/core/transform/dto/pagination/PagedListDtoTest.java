package com.wd.bym.core.transform.dto.pagination;

import com.wd.bym.context.AbstractMockitoTest;
import org.assertj.core.api.ObjectAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * Test les cas :
 *  - les valeurs par défaut
 *  - une liste de plusieurs éléments
 *  - le pager set à null
 *  - la liste set à null
 */
class PagedListDtoTest extends AbstractMockitoTest {

    public static Stream<Arguments> provideArguments() {
        return Stream.of(
                Arguments.of(PagedListDto.builder().build(),
                        Collections.EMPTY_LIST, 0, PagerDto.builder().build()), // expectations
                Arguments.of(PagedListDto.builder().pager(null).build(),
                        Collections.EMPTY_LIST, 0, null), // expectations
                Arguments.of(PagedListDto.builder().items(null).build(),
                        null, 0, PagerDto.builder().build()), // expectations
                Arguments.of(PagedListDto.builder().items(List.of("toto", "titi")).build(),
                        List.of("toto", "titi"), 0, PagerDto.builder().build()) // expectations
        );
    }

    @DisplayName("Testing with different parameters")
    @ParameterizedTest(name = "#{index} - PagedListDto should have a list, total = {2}, and a PagerDto")
    @MethodSource("provideArguments")
    void whenHaveNoParams_shouldHavePageAndPageSizeEqualsToZero(PagedListDto<?> pagedListDto,
                                                                List<?> expectedItems,
                                                                long expectedTotal,
                                                                PagerDto expectedPagerDto) {
        // ASSERT SORT FIELD
        ObjectAssert<?> assertGetItems = assertThat(pagedListDto.getItems());
        if(expectedItems == null) assertGetItems.isNull();
        else assertGetItems.isEqualTo(expectedItems);

        // ASSERT TOTAL
        assertThat(pagedListDto.getTotal()).isEqualTo(expectedTotal);

        // ASSERT SORT FIELD
        ObjectAssert<?> assertPager = assertThat(pagedListDto.getPager());
        if(expectedPagerDto == null) assertPager.isNull();
        else assertPager.isEqualTo(expectedPagerDto);
    }

}