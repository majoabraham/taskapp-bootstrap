/*
 * Created by 4Q developer (dev@4q.sk)
 * Copyright (c) 2019
 * 4Q s.r.o. All rights reserved.
 * http://www.4q.eu
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are not permitted.
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
 * FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 * COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
 * ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

package sk.fourq.mario.taskappbootstrap;

import sk.fourq.bootstrap.dao.jpa.AbstractDaoJpa;
import sk.fourq.bootstrap.domain.User_;
import sk.fourq.bootstrap.search.FindParams;
import sk.fourq.bootstrap.security.Configs;
import sk.fourq.bootstrap.util.BootstrapConfigKeys;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Stateless
public class UserProfileDaoJpa extends AbstractDaoJpa<UserProfile, Integer> implements UserProfileDao {

    private static Set<String> filterableFields;

    static {
        filterableFields = new HashSet<>();
        filterableFields.add("user.firstName");
        filterableFields.add("user.lastName");
    }
    @Inject
    private Configs configs;

    public UserProfileDaoJpa() {
        super(UserProfile.class);
    }

    @Override
    public void delete(UserProfile entity) {
        if (this.configs.getBoolean(BootstrapConfigKeys.EVENTS_ENABLED)) {
            Query query = this.em.createQuery("DELETE FROM UserEvent e WHERE e.user = :user");
            query.setParameter("user", entity);
            query.executeUpdate();
        }

        super.delete(entity);
    }

    @Override
    protected void customizeWhere(Map<String, Predicate> p, FindParams fp, CriteriaBuilder cb, CriteriaQuery<?> cq, Root<UserProfile> root) {
        String pattern = fp.getFulltextFilterPattern();
        if (pattern != null) {
            p.put(FP_SIMPLE_FULLTEXT_PREDICATE, cb.or(
                    cb.like(cb.lower(root.get(UserProfile_.user).get(User_.name)), pattern.toLowerCase(), FindParams.PATTERN_SPEC_CHAR),
                    cb.like(cb.lower(root.get(UserProfile_.user).get(User_.firstName)), pattern.toLowerCase(), FindParams.PATTERN_SPEC_CHAR),
                    cb.like(cb.lower(root.get(UserProfile_.user).get(User_.lastName)), pattern.toLowerCase(), FindParams.PATTERN_SPEC_CHAR)
            ));
        }

        if (fp.getUsers() != null) {
            p.put(FP_OWNER_PREDICATE, root.get(UserProfile_.user).in(fp.getUsers()));
        }
    }

    @Override
    public Set<String> getFilterableFields() {
        return Collections.unmodifiableSet(filterableFields);
    }
}
