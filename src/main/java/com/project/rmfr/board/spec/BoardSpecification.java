package com.project.rmfr.board.spec;

import com.project.rmfr.board.entity.AllNoticeContents;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class BoardSpecification {

    public static Specification<AllNoticeContents> withAncState(int ancState) {
        return (Specification<AllNoticeContents>) ((root, query, builder) ->
                builder.equal(root.get("ancState"), ancState)
        );
    }
}
