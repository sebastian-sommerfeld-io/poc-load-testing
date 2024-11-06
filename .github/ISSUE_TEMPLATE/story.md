---
name: Story
about: Describe a new feature (only one single feature at best)
title: ''
labels: story, needs-triage
assignees: ''
---

:bulb: **Info:** Use a user story when you want to describe a feature or function from the perspective of the end user’s needs and behaviors, typically to capture who they are, what they want, and why they need it. It's most useful when the user's identity and context directly influence the solution.

:zap: As a _role_ I can _capability_, so that _receive benefit_.

_bulb: **Info:** Use a job story when the focus is on the specific situation, allowing for more granular insights into what triggers the need. Job stories are ideal for identifying opportunities for improvement or innovation without centering on a particular user persona.

:zap: When _some situation_, I want to _do something_, so I can _achieve this outcome_.

### Acceptance Criteria (executable specification)

:zap: Write the Acceptance Criteria ... The Scenarios names are pointing in the right direction, but are nowhere near ready for implementation.

```gherkin
Feature: todo ...

  As a _role_ I can _capability_, so that _receive benefit_.

  Background:
    Given todo ...

  Scenario: todo ...
    Given todo ...
    When todo ...
    Then todo ...
```

### Acceptance Criteria (additional / manual)

:zap: Memo (remove when story is ready)

- [ ] :zap: Translate the user story into executable specifications
    - :zap: If you cannot think about phrasing these tests, check if you really understand the problem
    - :zap: Use ubiquitous language based on the language of the problem domain
    - :zap: When a dedicated file from the code repository is used to define the acceptance criteria, do not repeat here. Just link to the proper definitions

### Post-Implementation Tasks (Examples)

- [ ] :zap: Measure _related metric_ after implementation for _period of time_.
- [ ] :zap: Track the reduction in _related metric_ related to the feature for _period of time_.
