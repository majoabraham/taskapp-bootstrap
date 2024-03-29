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

import javax.ejb.Stateless;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Stateless
public class TaskDaoJpa extends AbstractDaoJpa<Task, Integer> implements TaskDao {

    private static Set<String> filterableFields;

    static {
        filterableFields = new HashSet<>();
        filterableFields.add("description");
    }

    public TaskDaoJpa() {
        super(Task.class);
    }

    @Override
    public Set<String> getFilterableFields() {

        return Collections.unmodifiableSet(filterableFields);
    }
}
